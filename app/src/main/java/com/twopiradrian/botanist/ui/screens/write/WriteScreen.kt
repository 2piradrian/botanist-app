package com.twopiradrian.botanist.ui.screens.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun WriteScreen(
    navController: NavController,
    navigationType: NavigationType
) {
    AppLayout(navController = navController, navigationType = navigationType) {
        Body()
    }
}

@Composable
fun Body() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleLarge(
            textId = R.string.write_title
        )
    }
}