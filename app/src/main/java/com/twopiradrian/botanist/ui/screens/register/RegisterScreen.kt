package com.twopiradrian.botanist.ui.screens.register

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.title.Title
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun RegisterScreen(
    navController: NavController
) {
    AppLayout(navController = navController) {
        Body()
    }
}

@Composable
fun Body(){
    Title(text = R.string.register_title)
}