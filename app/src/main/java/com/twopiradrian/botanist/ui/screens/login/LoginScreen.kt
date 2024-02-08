package com.twopiradrian.botanist.ui.screens.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.core.app.Constants.Companion.MAX_WIDTH
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.ui.components.button.MainButton
import com.twopiradrian.botanist.ui.components.button.PlainButton
import com.twopiradrian.botanist.ui.components.input.InputData
import com.twopiradrian.botanist.ui.components.input.InputType
import com.twopiradrian.botanist.ui.components.input.OutlinedInput
import com.twopiradrian.botanist.ui.components.title.TitleMedium
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.layout.FormLayout
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {

    val emailInput by viewModel.emailInput.collectAsState()
    val passwordInput by viewModel.passwordInput.collectAsState()

    AppLayout(
        modifier = Modifier.widthIn(max = MAX_WIDTH),
        navController = navController,
        adaptiveWidth = false,
        withNavigationBar = false,
    ) {
        Body(
            viewModel = viewModel,
            navController = navController,
            emailInput = emailInput,
            passwordInput = passwordInput
        )

    }
}

@Composable
fun Body(
    viewModel: LoginViewModel,
    navController: NavController,
    emailInput: InputData,
    passwordInput: InputData,
) {
    FormLayout {
        TitleMedium(text = R.string.login_title)
        Divider(
            modifier = Modifier.padding(20.dp)
        )
        OutlinedInput(
            state = emailInput,
            inputType = InputType.EMAIL,
            label = R.string.email_label,
            placeholder = R.string.email_placeholder,
            onValueChange = { viewModel.onLoginChange(it, passwordInput.state) },
            isLastField = false,
            icon = Icons.Default.Email
        )
        OutlinedInput(
            state = passwordInput,
            inputType = InputType.PASSWORD,
            label = R.string.password_label,
            placeholder = R.string.password_placeholder,
            onValueChange = { viewModel.onLoginChange(emailInput.state, it) },
            isLastField = true,
            icon = Icons.Default.Lock
        )
        Spacer(modifier = Modifier.padding(12.dp))
        MainButton(
            isEnabled = true,
            text = R.string.login_button,
            onClick = {  }
        )
        Spacer(modifier = Modifier.padding(18.dp))
        PlainButton(
            text = R.string.login_register,
            onClick = {
                navController.navigate(AppScreens.Register.route)
            }
        )
    }
}