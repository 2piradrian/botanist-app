package com.twopiradrian.botanist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.screens.explore.ExploreScreen
import com.twopiradrian.botanist.ui.screens.login.LoginScreen
import com.twopiradrian.botanist.ui.screens.profile.ProfileScreen
import com.twopiradrian.botanist.ui.screens.register.RegisterScreen
import com.twopiradrian.botanist.ui.screens.write.WriteScreen

@Composable
fun AppNavigation(
    navigationType: NavigationType,
    contentType: ContentType
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Explore.route) {
        composable(AppScreens.Login.route) {
            LoginScreen(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType
            )
        }
        composable(AppScreens.Register.route) {
            RegisterScreen(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType
            )
        }
        composable(AppScreens.Explore.route) {
            ExploreScreen(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType
            )
        }
        composable(AppScreens.Write.route) {
            WriteScreen(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType
            )
        }
        composable(AppScreens.Profile.route) {
            ProfileScreen(
                navController = navController,
                navigationType = navigationType,
                contentType = contentType
            )
        }
    }
}