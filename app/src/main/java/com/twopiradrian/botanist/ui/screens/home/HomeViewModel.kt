package com.twopiradrian.botanist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.usecase.auth.RefreshTokens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _isShowingHomePage = MutableStateFlow(true)
    val isShowingHomePage: StateFlow<Boolean> = _isShowingHomePage

    private val _isUserLoggedIn = MutableStateFlow(true)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun setIsShowingHomePage(b: Boolean) {
        _isShowingHomePage.value = b
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
}