package com.twopiradrian.botanist.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    AppLayout(navController = navController) {

    }
}

@Composable
fun Body(){}