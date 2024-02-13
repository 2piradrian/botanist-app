package com.twopiradrian.botanist.ui.screens.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.input.FilledInput
import com.twopiradrian.botanist.ui.components.input.InputData
import com.twopiradrian.botanist.ui.components.input.InputType
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
        Spacer(modifier = Modifier.height(12.dp))
        FilledInput(
            state = InputData.empty(),
            inputType = InputType.TEXT,
            placeholder = R.string.write_post_title,
            onValueChange = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilledInput(
            state = InputData.empty(),
            inputType = InputType.TEXT,
            placeholder = R.string.write_post_description,
            onValueChange = {},
            maxLines = 5,
            singleLine = false,
            modifier = Modifier.heightIn(min = 140.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        FilledInput(
            state = InputData.empty(),
            inputType = InputType.TEXT,
            placeholder = R.string.write_post_content,
            onValueChange = {},
            maxLines = 200,
            singleLine = false,
            modifier = Modifier.heightIn(min = 300.dp)
        )
    }
}