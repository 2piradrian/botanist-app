package com.twopiradrian.botanist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twopiradrian.botanist.ui.screens.home.HomeScreen
import com.twopiradrian.botanist.ui.screens.home.HomeViewModel
import com.twopiradrian.botanist.ui.screens.login.LoginScreen
import com.twopiradrian.botanist.ui.screens.login.LoginViewModel
import com.twopiradrian.botanist.ui.screens.register.RegisterScreen
import com.twopiradrian.botanist.ui.screens.register.RegisterViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Home.route) {
        composable(AppScreens.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(AppScreens.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(AppScreens.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}