package com.twopiradrian.botanist.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.domain.usecase.user.GetProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    private val _userProfile = MutableStateFlow<UserEntity?>(null)
    val userProfile: StateFlow<UserEntity?> = _userProfile

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
}