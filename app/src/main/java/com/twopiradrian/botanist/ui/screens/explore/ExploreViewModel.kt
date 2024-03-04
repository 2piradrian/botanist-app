package com.twopiradrian.botanist.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    private val pageSize = 3

    private val _isShowingMainScreen = MutableStateFlow(true)
    val isShowingMainScreen: StateFlow<Boolean> = _isShowingMainScreen

    private val _selectedPost = MutableStateFlow<PostEntity?>(null)
    val selectedPost: StateFlow<PostEntity?> = _selectedPost

    private val _posts = MutableStateFlow(emptyList<PostEntity>())
    val posts: StateFlow<List<PostEntity>> = _posts

    private val _categories = MutableStateFlow(emptyList<Categories>())
    val categoriesFlow: StateFlow<List<Categories>> = _categories

    private val _userProfile = MutableStateFlow<UserEntity?>(null)
    val userProfile: StateFlow<UserEntity?> = _userProfile

    private val _page = MutableStateFlow<Int?>(1)
    val page: StateFlow<Int?> = _page

    private val _error = MutableStateFlow(0)
    val error: StateFlow<Int> = _error

    fun changeErrorState() {
        _error.value = 0
    }

    fun setIsShowingMainScreen(b: Boolean){
        _isShowingMainScreen.value = b
    }

    fun setSelectedPost(post: PostEntity){
        _selectedPost.value = post
    }

    fun resetPagination() {
        _posts.value = emptyList()
        _categories.value = emptyList()
        _page.value = 1
    }

    fun likePost (session: Session, post: PostEntity, user: UserEntity?){
        viewModelScope.launch {
            val tokens = session.getTokens()

            val result = try {
                val request = LikePost.Request(post.id)
                LikePost().invoke(tokens, request)
            } catch (e: Exception){
                e.printStackTrace()
                null
            }

            result?.response?.let {
                if (user != null) {
                    val list = user.likes.toMutableList()

                    if (list.contains(post.id)) {
                        list.remove(post.id)
                    }
                    else {
                        list.add(post.id)
                    }
                    _userProfile.value = user.copy(likes = list)
                }
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                return@launch
            }

            _error.value = R.string.server_error
        }
    }


    fun followUser(session: Session, post: PostEntity, user: UserEntity?){
        viewModelScope.launch {
            val tokens = session.getTokens()
            val followedId = post.authorId

            val result = try {
                val request = FollowUser.Request(followedId)
                FollowUser().invoke(tokens, request)
            } catch (e: Exception){
                e.printStackTrace()
                null
            }

            result?.response?.let {
                if (user != null) {
                    val list = user.following.toMutableList()

                    if (list.contains(post.authorId)) {
                        list.remove(post.authorId)
                    }
                    else {
                        list.add(post.authorId)
                    }
                    _userProfile.value = user.copy(following = list)
                }
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                return@launch
            }

            _error.value = R.string.server_error
        }
    }

    fun setCategories(category: Categories){
        val list = _categories.value.toMutableList()

        if (list.contains(category)) {
            list.remove(category)
        } else{
            list.add(category)
        }
        _page.value = 1
        _categories.value = list
    }

    suspend fun getPosts(
        session: Session,
        categories: List<Categories> = emptyList(),
        postList: List<PostEntity> = emptyList(),
        selectedPost: PostEntity? = null,
        page: Int?,
    ){
        viewModelScope.launch {

            if (page == null) return@launch

            val result = try {
                val request = GetByCategories.Request(
                    categories = categories,
                    page = page,
                    pageSize = pageSize
                )
                val tokens = session.getTokens()

                GetByCategories().invoke(tokens, request)
            }
            catch (e: Exception){
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _posts.value = postList + it.posts
                _page.value = it.nextPage

                if (selectedPost == null && it.posts.isNotEmpty()) {
                    _selectedPost.value = it.posts.first()
                }
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                _page.value = null
                return@launch
            }

            _error.value = R.string.server_error
        }
    }

    suspend fun getUserProfile(session: Session) {
        viewModelScope.launch {
            val tokens = session.getTokens()
            val user = session.getUser()

            val request = GetProfile.Request(profile = user.id, includePosts = false)
            val result = try {
                GetProfile().invoke(tokens, request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _userProfile.value = it.user
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                _page.value = null
                return@launch
            }

            _error.value = R.string.server_error
        }
    }

    fun checkIfUserIsLoggedIn(session: Session): Boolean {
        val user = session.getUser()
        val tokens = session.getTokens()

        if (user.id.isEmpty() || tokens.accessToken.isEmpty() || tokens.refreshToken.isEmpty()) {
            return false
        }
        refreshTokens(session)
        return true
    }

    private fun refreshTokens(session: Session) {
        viewModelScope.launch {
            val result = try {
                val token = session.getTokens().refreshToken
                val request = RefreshTokens.Request(token)

                RefreshTokens().invoke(request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                val tokens = TokensEntity(
                    accessToken = it.accessToken,
                    refreshToken = it.refreshToken
                )

                session.saveTokens(tokens)

                return@launch
            }
        }
    }

}