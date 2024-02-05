package com.twopiradrian.botanist.core.navigation

sealed class AppScreens(val route: String) {
    data object Register : AppScreens("register")
    data object Login : AppScreens("login")
}