package com.twopiradrian.botanist.ui.screens.write

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.input.CategoryMenu
import com.twopiradrian.botanist.ui.components.input.FilledInput
import com.twopiradrian.botanist.ui.components.input.InputData
import com.twopiradrian.botanist.ui.components.input.InputType
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.ui.components.button.ImagePickerButton
import com.twopiradrian.botanist.ui.components.button.MainButton
import com.twopiradrian.botanist.ui.layout.FormLayout

@Composable
fun WriteScreen(
    navController: NavController,
    navigationType: NavigationType,
    viewModel: WriteViewModel = viewModel()
) {
    val context = LocalContext.current

    val titleInput by viewModel.title.collectAsState()
    val descriptionInput by viewModel.description.collectAsState()
    val categoryInput by viewModel.category.collectAsState()
    val contentInput by viewModel.content.collectAsState()

    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    AppLayout(navController = navController, navigationType = navigationType) {
        Body(
            titleInput = titleInput,
            descriptionInput = descriptionInput,
            categoryInput = categoryInput,
            contentInput = contentInput,
            isButtonEnabled = isButtonEnabled
        )
    }
}

@Composable
fun Body(
    titleInput: InputData,
    descriptionInput: InputData,
    categoryInput: InputData,
    contentInput: InputData,
    isButtonEnabled: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        FormLayout {
            TitleLarge(
                textId = R.string.write_title
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            FilledInput(
                state = titleInput,
                inputType = InputType.TEXT,
                placeholder = R.string.write_post_title,
                onValueChange = {}
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            FilledInput(
                state = descriptionInput,
                inputType = InputType.TEXT,
                placeholder = R.string.write_post_description,
                onValueChange = {},
                maxLines = 5,
                singleLine = false,
                modifier = Modifier.heightIn(min = 120.dp)
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            CategoryMenu(
                state = categoryInput
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            ImagePickerButton(
                text = R.string.photo_label,
                onClick = {}
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            FilledInput(
                state = contentInput,
                inputType = InputType.TEXT,
                placeholder = R.string.write_post_content,
                onValueChange = {},
                maxLines = 200,
                singleLine = false,
                modifier = Modifier.heightIn(min = 300.dp)
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            MainButton(
                isEnabled = isButtonEnabled,
                text = R.string.write_post_button,
                onClick = {}
            )
        }
    }
}