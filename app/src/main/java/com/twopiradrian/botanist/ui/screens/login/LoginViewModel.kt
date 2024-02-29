package com.twopiradrian.botanist.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.TokensEntity
import com.twopiradrian.botanist.domain.entity.UserDataEntity
import com.twopiradrian.botanist.domain.usecase.auth.Login
import com.twopiradrian.botanist.ui.app.InputData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _emailInput = MutableStateFlow(InputData.empty())
    val emailInput: StateFlow<InputData> = _emailInput

    private val _passwordInput = MutableStateFlow(InputData.empty())
    val passwordInput: StateFlow<InputData> = _passwordInput

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    private val _userLogged = MutableStateFlow(false)
    val userLogged: StateFlow<Boolean> = _userLogged

    private val _error = MutableStateFlow(0)
    val error: StateFlow<Int> = _error

    fun changeErrorState() {
        _error.value = 0
    }

    fun loginUser(email: String, password: String, session: Session) {
        viewModelScope.launch {
            val result = try {
                val request = Login.Request(email, password)
                Login().invoke(request)
            }
            catch (e: Exception) {
                null
            }

            result?.response?.let {
                saveData(it, session)
                _error.value = 0
                _userLogged.value = true
                return@launch
            }

            result?.error?.let {
                _error.value = result.error
                _userLogged.value = false
                return@launch
            }

            _error.value = R.string.server_error
            _userLogged.value = false
        }
    }

    private fun saveData(
        result: Login.Response, session: Session
    ) {

        val userResponse = result.user
        val tokensResponse = result.tokens

        userResponse.let {
            val userId = it.id
            val userEmail = it.email
            val user = UserDataEntity(
                userId, userEmail
            )
            session.saveUser(user)
        }

        tokensResponse.let {
            val accessToken = it.accessToken
            val refreshToken = it.refreshToken
            val tokens = TokensEntity(
                accessToken, refreshToken
            )
            session.saveTokens(tokens)
        }
    }

    fun onLoginChange(
        email: String, password: String
    ) {
        _emailInput.update {
            it.copy(state = email, isError = !isEmailValid(email))
        }
        _passwordInput.update {
            it.copy(state = password, isError = !isPasswordValid(password))
        }
        _isButtonEnabled.value = enableLoginButton(email, password)
    }


    private fun isEmailValid(email: String): Boolean {
        if (email.isEmpty()) {
            _emailInput.update {
                it.copy(errorState = R.string.error_required_field)
            }
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailInput.update {
                it.copy(errorState = R.string.error_invalid_email)
            }
            return false
        }
        else {
            _emailInput.update {
                it.copy(errorState = 0)
            }
        }
        return true
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.isEmpty()) {
            _passwordInput.update {
                it.copy(errorState = R.string.error_required_field)
            }
            return false
        }
        else if (password.length < 8) {
            _passwordInput.update {
                it.copy(errorState = R.string.error_invalid_password)
            }
            return false
        }
        else {
            _passwordInput.update {
                it.copy(errorState = 0)
            }
        }
        return true
    }

    private fun enableLoginButton(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

}