package com.twopiradrian.botanist.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.core.app.Constants.Companion.MAX_WIDTH
import com.twopiradrian.botanist.ui.components.atoms.MainButton
import com.twopiradrian.botanist.ui.app.InputData
import com.twopiradrian.botanist.ui.app.InputType
import com.twopiradrian.botanist.ui.components.atoms.OutlinedInput
import com.twopiradrian.botanist.ui.components.atoms.TitleMedium
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.layout.FormLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout


@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel(),
    navigationType: NavigationType,
    contentType: ContentType,
) {
    val context = LocalContext.current

    val emailInput by viewModel.emailInput.collectAsState()
    val passwordInput by viewModel.passwordInput.collectAsState()
    val usernameInput by viewModel.usernameInput.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    val error by viewModel.error.collectAsState()
    val userRegistered by viewModel.userRegistered.collectAsState()

    LaunchedEffect(userRegistered) {
        if (userRegistered) {
            Toast.makeText(context, context.getString(R.string.register_success), Toast.LENGTH_LONG).show()
            navController.navigate(AppScreens.Login.route)
        }
    }

    LaunchedEffect(error) {
        if (error != 0) {
            Toast.makeText(context, context.getString(error), Toast.LENGTH_LONG).show()
        }
        viewModel.changeErrorState()
    }

    AppLayout(
        modifier = Modifier.widthIn(max = MAX_WIDTH),
        navController = navController,
        navigationType = navigationType,
        withNavigationBar = false,
    ) {
        AdaptiveLayout(
            screen1 = {
                Body(
                    viewModel = viewModel,
                    emailInput = emailInput,
                    passwordInput = passwordInput,
                    usernameInput = usernameInput,
                    isButtonEnabled = isButtonEnabled
                )
            },
            contentType = contentType
        )
    }
}

@Composable
fun Body(
    viewModel: RegisterViewModel,
    emailInput: InputData,
    passwordInput: InputData,
    usernameInput: InputData,
    isButtonEnabled: Boolean,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            TitleMedium(textId = R.string.register_title)
            HorizontalDivider(
                modifier = Modifier.padding(20.dp)
            )
            OutlinedInput(
                state = usernameInput,
                inputType = InputType.TEXT,
                label = R.string.username_label,
                placeholder = R.string.username_placeholder,
                onValueChange = {
                    viewModel.onRegisterChange(
                        emailInput.state,
                        passwordInput.state,
                        it
                    )
                },
                isLastField = false,
                icon = Icons.Default.AccountCircle
            )
            OutlinedInput(
                state = emailInput,
                inputType = InputType.EMAIL,
                label = R.string.email_label,
                placeholder = R.string.email_placeholder,
                onValueChange = {
                    viewModel.onRegisterChange(
                        it,
                        passwordInput.state,
                        usernameInput.state
                    )
                },
                isLastField = false,
                icon = Icons.Default.Email
            )
            OutlinedInput(
                state = passwordInput,
                inputType = InputType.PASSWORD,
                label = R.string.password_label,
                placeholder = R.string.password_placeholder,
                onValueChange = {
                    viewModel.onRegisterChange(
                        emailInput.state,
                        it,
                        usernameInput.state
                    )
                },
                isLastField = true,
                icon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.padding(12.dp))
            MainButton(
                isEnabled = isButtonEnabled,
                text = R.string.register_button,
                onClick = {
                    viewModel.registerUser(
                        emailInput.state,
                        passwordInput.state,
                        usernameInput.state
                    )
                }
            )
        }
    }

}