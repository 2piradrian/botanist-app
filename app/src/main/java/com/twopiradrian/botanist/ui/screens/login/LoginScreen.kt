package com.twopiradrian.botanist.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
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
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.ui.components.atoms.MainButton
import com.twopiradrian.botanist.ui.components.atoms.PlainButton
import com.twopiradrian.botanist.ui.app.InputData
import com.twopiradrian.botanist.ui.app.InputType
import com.twopiradrian.botanist.ui.components.atoms.OutlinedInput
import com.twopiradrian.botanist.ui.components.atoms.TitleMedium
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.layout.FormLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    navigationType: NavigationType,
    contentType: ContentType
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val emailInput by viewModel.emailInput.collectAsState()
    val passwordInput by viewModel.passwordInput.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    val error by viewModel.error.collectAsState()
    val userLogged by viewModel.userLogged.collectAsState()

    LaunchedEffect(userLogged) {
        if (userLogged) {
            Toast.makeText(context, context.getString(R.string.login_success), Toast.LENGTH_LONG).show()
            navController.navigate(AppScreens.Explore.route)
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
                    navController = navController,
                    session = session,
                    emailInput = emailInput,
                    passwordInput = passwordInput,
                    isButtonEnabled = isButtonEnabled
                )
            },
            contentType = contentType,
        )
    }
}

@Composable
fun Body(
    viewModel: LoginViewModel,
    navController: NavController,
    session: Session,
    emailInput: InputData,
    passwordInput: InputData,
    isButtonEnabled: Boolean,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormLayout {
            TitleMedium(textId = R.string.login_title)
            HorizontalDivider(
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
                isEnabled = isButtonEnabled,
                text = R.string.login_button,
                onClick = {
                    viewModel.loginUser(
                        email = emailInput.state, password = passwordInput.state, session = session
                    )
                }
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
}