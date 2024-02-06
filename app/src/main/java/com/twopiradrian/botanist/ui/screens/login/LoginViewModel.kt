package com.twopiradrian.botanist.ui.screens.login

import android.util.Patterns
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.input.InputData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel {
    private val _emailInput = MutableStateFlow(InputData.empty())
    val emailInput: StateFlow<InputData> = _emailInput

    private val _passwordInput = MutableStateFlow(InputData.empty())
    val passwordInput: StateFlow<InputData> = _passwordInput

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    private val _error = MutableStateFlow(0)
    val error: StateFlow<Int> = _error

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