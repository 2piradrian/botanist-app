package com.twopiradrian.botanist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.screens.home.HomeScreen
import com.twopiradrian.botanist.ui.screens.login.LoginScreen
import com.twopiradrian.botanist.ui.screens.register.RegisterScreen

@Composable
fun AppNavigation(
    navigationType: NavigationType
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Home.route) {
        composable(AppScreens.Login.route) {
            LoginScreen(
                navController = navController,
                navigationType = navigationType
            )
        }
        composable(AppScreens.Register.route) {
            RegisterScreen(
                navController = navController,
                navigationType = navigationType
            )
        }
        composable(AppScreens.Home.route) {
            HomeScreen(
                navController = navController,
                navigationType = navigationType
            )
        }
    }
}