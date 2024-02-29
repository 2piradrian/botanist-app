package com.twopiradrian.botanist.ui.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.usecase.auth.Register
import com.twopiradrian.botanist.ui.app.InputData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _emailInput = MutableStateFlow(InputData.empty())
    val emailInput: StateFlow<InputData> = _emailInput

    private val _passwordInput = MutableStateFlow(InputData.empty())
    val passwordInput: StateFlow<InputData> = _passwordInput

    private val _usernameInput = MutableStateFlow(InputData.empty())
    val usernameInput: StateFlow<InputData> = _usernameInput

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    private val _error = MutableStateFlow(0)
    val error: StateFlow<Int> = _error

    private val _userRegistered = MutableStateFlow(false)
    val userRegistered: StateFlow<Boolean> = _userRegistered

    fun changeErrorState() {
        _error.value = 0
    }

    fun registerUser(
        email: String, password: String, username: String
    ) {
        viewModelScope.launch {
            val result = try {
                val request = Register.Request(email, password, username)
                Register().invoke(request)
            }
            catch (e: Exception) {
                null
            }

            result?.response?.let {
                _error.value = 0
                _userRegistered.value = true
                return@launch
            }

            result?.error?.let {
                _error.value = it
                _userRegistered.value = false
                return@launch
            }

            _error.value = R.string.server_error
            _userRegistered.value = false
        }
    }

    fun onRegisterChange(
        email: String, password: String, username: String
    ) {
        _emailInput.update {
            it.copy(state = email, isError = !isEmailValid(email))
        }
        _passwordInput.update {
            it.copy(state = password, isError = !isPasswordValid(password))
        }
        _usernameInput.update {
            it.copy(state = username, isError = !isUsernameValid(username))
        }
        _isButtonEnabled.value = enableRegisterButton(email, password, username)
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

    private fun isUsernameValid(username: String): Boolean {
        if (username.isEmpty()) {
            _usernameInput.update {
                it.copy(errorState = R.string.error_required_field)
            }
            return false
        }
        else if (username.length < 5) {
            _usernameInput.update {
                it.copy(errorState = R.string.error_invalid_username)
            }
            return false
        }
        else {
            _usernameInput.update {
                it.copy(errorState = 0)
            }
        }
        return true
    }

    private fun enableRegisterButton(email: String, password: String, username: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password) && isUsernameValid(username)
    }

}