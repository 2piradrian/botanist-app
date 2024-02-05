package com.twopiradrian.botanist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twopiradrian.botanist.ui.screens.login.LoginScreen
import com.twopiradrian.botanist.ui.screens.register.RegisterScreen
import com.twopiradrian.botanist.ui.screens.register.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Register.route) {
        composable(AppScreens.Login.route) {
            LoginScreen()
        }
        composable(AppScreens.Register.route) {
            RegisterScreen(navController = navController, viewModel = RegisterViewModel())
        }
    }
}