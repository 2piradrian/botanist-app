package com.twopiradrian.botanist.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.domain.usecase.post.Delete
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val _isShowingMainScreen = MutableStateFlow(true)
    val isShowingMainScreen: StateFlow<Boolean> = _isShowingMainScreen

    private val _selectedPost = MutableStateFlow<PostEntity?>(null)
    val selectedPost: StateFlow<PostEntity?> = _selectedPost

    private val _posts = MutableStateFlow(emptyList<PostEntity>())
    val posts: StateFlow<List<PostEntity>> = _posts

    private val _userProfile = MutableStateFlow<UserEntity?>(null)
    val userProfile: StateFlow<UserEntity?> = _userProfile

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

    suspend fun deletePost(session: Session, postId: String){
        viewModelScope.launch {
            val tokens = session.getTokens()
            val request = Delete.Request(postId)

            val result = try {
                Delete().invoke(tokens.accessToken, request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                getUserProfile(session)
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                return@launch
            }

            _error.value = R.string.server_error
        }
    }

    suspend fun getUserProfile(session: Session) {
        viewModelScope.launch {
            val tokens = session.getTokens()
            val user = session.getUser()

            val request = GetProfile.Request(profile = user.id, includePosts = true)
            val result = try {
                GetProfile().invoke(tokens, request)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            result?.response?.let {
                _userProfile.value = it.user
                _posts.value = it.posts

                if (it.posts.isNotEmpty()) {
                    _selectedPost.value = it.posts[0]
                }
                else{
                    _selectedPost.value = null
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
}