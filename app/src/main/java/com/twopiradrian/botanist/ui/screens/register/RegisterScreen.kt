package com.twopiradrian.botanist.ui.screens.register

import android.graphics.drawable.Icon
import android.widget.Space
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.button.MainButton
import com.twopiradrian.botanist.ui.components.input.InputData
import com.twopiradrian.botanist.ui.components.input.InputType
import com.twopiradrian.botanist.ui.components.input.OutlinedInput
import com.twopiradrian.botanist.ui.components.title.TitleMedium
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {

    val emailInput by viewModel.emailInput.collectAsState()
    val passwordInput by viewModel.passwordInput.collectAsState()
    val usernameInput by viewModel.usernameInput.collectAsState()

    AppLayout(navController = navController) {
        Body(
            viewModel = viewModel,
            emailInput = emailInput,
            passwordInput = passwordInput,
            usernameInput = usernameInput,
        )
    }
}

@Composable
fun Body(
    viewModel: RegisterViewModel,
    emailInput: InputData,
    passwordInput: InputData,
    usernameInput: InputData,
) {

    TitleMedium(text = R.string.register_title)
    Divider(
        modifier = Modifier.padding(20.dp)
    )
    OutlinedInput(
        state = usernameInput,
        inputType = InputType.TEXT,
        label = R.string.username_label,
        placeholder = R.string.username_placeholder,
        onValueChange = { viewModel.onRegisterChange(emailInput.state, passwordInput.state, it) },
        isLastField = false,
        icon = Icons.Default.AccountCircle
    )
    OutlinedInput(
        state = emailInput,
        inputType = InputType.EMAIL,
        label = R.string.email_label,
        placeholder = R.string.email_placeholder,
        onValueChange = { viewModel.onRegisterChange(it, passwordInput.state, usernameInput.state) },
        isLastField = false,
        icon = Icons.Default.Email
    )
    OutlinedInput(
        state = passwordInput,
        inputType = InputType.PASSWORD,
        label = R.string.password_label,
        placeholder = R.string.password_placeholder,
        onValueChange = { viewModel.onRegisterChange(emailInput.state, it, usernameInput.state) },
        isLastField = true,
        icon = Icons.Default.Lock
    )
    Spacer(modifier = Modifier.padding(12.dp))
    MainButton(isEnabled = true, text = R.string.register_button, onClick = {  })
}