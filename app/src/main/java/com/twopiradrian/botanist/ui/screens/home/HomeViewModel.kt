package com.twopiradrian.botanist.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import com.twopiradrian.botanist.domain.usecase.post.GetByCategories
import com.twopiradrian.botanist.domain.usecase.user.FollowUser
import com.twopiradrian.botanist.domain.usecase.user.GetFeed
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import com.twopiradrian.botanist.domain.usecase.user.LikePost
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var page: Int? = 1
    private val pageSize: Int = 3

    private val _isShowingMainScreen = MutableStateFlow(true)
    val isShowingMainScreen: StateFlow<Boolean> = _isShowingMainScreen

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private val _userProfile = MutableStateFlow<UserEntity?>(null)
    val userProfile: StateFlow<UserEntity?> = _userProfile

    private val _selectedPost = MutableStateFlow<PostEntity?>(null)
    val selectedPost: StateFlow<PostEntity?> = _selectedPost

    private val _posts = MutableStateFlow(emptyList<PostEntity>())
    val posts: StateFlow<List<PostEntity>> = _posts

    fun setIsShowingMainScreen(b: Boolean) {
        _isShowingMainScreen.value = b
    }

    fun setSelectedPost(post: PostEntity){
        _selectedPost.value = post
    }

    fun checkIfUserIsLoggedIn(session: Session) {
        val user = session.getUser()
        val tokens = session.getTokens()

        if (user.id.isEmpty() || tokens.accessToken.isEmpty() || tokens.refreshToken.isEmpty()) {
            _isUserLoggedIn.value = false
            return
        }
        refreshTokens(session)
    }

    suspend fun getPosts(
        session: Session,
        postList: List<PostEntity> = emptyList(),
        selectedPost: PostEntity? = null
    ){
        viewModelScope.launch {
            if (page == null) return@launch

            val result = try {
                val request = GetFeed.Request(
                    page = page!!,
                    pageSize = pageSize
                )
                val tokens = session.getTokens()

                GetFeed().invoke(tokens, request)
            }
            catch (e: Exception){
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _posts.value = postList + it.posts
                page = it.nextPage

                if (selectedPost == null) {
                    _selectedPost.value = it.posts.first()
                }
            }

            result?.error?.let {
                page = null
            }

        }
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
                _isUserLoggedIn.value = true

                return@launch
            }

            _isUserLoggedIn.value = false
        }
    }

    suspend fun getUserProfile(session: Session) {
        viewModelScope.launch {
            val tokens = session.getTokens()
            val user = session.getUser()

            val request = GetProfile.Request(user.id)
            val result = try {
                GetProfile().invoke(tokens, request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _userProfile.value = it.user
            }

            result?.error?.let {
                Log.d("ExploreViewModel", "Error getting user profile")
            }
        }
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
            }

            result?.error?.let {
                Log.d("ExploreViewModel", "Error liking post")
            }
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
            }

            result?.error?.let {
                Log.d("ExploreViewModel", "Error following user")
            }
        }
    }
}