package com.twopiradrian.botanist.ui.screens.write

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.ui.components.button.ImagePickerButton
import com.twopiradrian.botanist.ui.components.button.SecondaryButton
import com.twopiradrian.botanist.ui.layout.FormLayout

@Composable
fun WriteScreen(
    navController: NavController,
    navigationType: NavigationType,
    viewModel: WriteViewModel = viewModel()
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val titleInput by viewModel.title.collectAsState()
    val descriptionInput by viewModel.description.collectAsState()
    val categoryInput by viewModel.category.collectAsState()
    val imageInput by viewModel.image.collectAsState()
    val contentInput by viewModel.content.collectAsState()

    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    AppLayout(navController = navController, navigationType = navigationType) {
        Body(
            viewModel = viewModel,
            context = context,
            session = session,
            // --
            titleInput = titleInput,
            descriptionInput = descriptionInput,
            categoryInput = categoryInput,
            imageInput = imageInput,
            contentInput = contentInput,
            isButtonEnabled = isButtonEnabled
        )
    }
}

@Composable
fun Body(
    viewModel: WriteViewModel,
    context: Context,
    session: Session,
    // --
    titleInput: InputData,
    descriptionInput: InputData,
    categoryInput: InputData,
    imageInput: Uri?,
    contentInput: InputData,
    isButtonEnabled: Boolean,

) {
    Column(
        modifier = Modifier.fillMaxSize()
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
                onValueChange = {
                    viewModel.onPostChange(
                        title = it,
                        description = descriptionInput.state,
                        category = categoryInput.state,
                        content = contentInput.state
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            FilledInput(
                state = descriptionInput,
                inputType = InputType.TEXT,
                placeholder = R.string.write_post_description,
                onValueChange = {
                    viewModel.onPostChange(
                        title = titleInput.state,
                        description = it,
                        category = categoryInput.state,
                        content = contentInput.state
                    )
                },
                maxLines = 5,
                singleLine = false,
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            CategoryMenu(
                state = categoryInput,
                onValueChange = {
                    viewModel.onPostChange(
                        title = titleInput.state,
                        description = descriptionInput.state,
                        category = it,
                        content = contentInput.state
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            ImagePickerButton(
                state = imageInput,
                updateState = { viewModel.onImageChange(it) }
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            FilledInput(
                state = contentInput,
                inputType = InputType.TEXT,
                placeholder = R.string.write_post_content,
                onValueChange = {
                    viewModel.onPostChange(
                        title = titleInput.state,
                        description = descriptionInput.state,
                        category = categoryInput.state,
                        content = it
                    )
                },
                maxLines = 200,
                singleLine = false,
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            SecondaryButton(
                isEnabled = isButtonEnabled,
                text = R.string.write_post_button,
                onClick = {
                    viewModel.createPost(
                        title = titleInput.state,
                        description = descriptionInput.state,
                        category = categoryInput.state,
                        content = contentInput.state,
                        image = imageInput,
                        tokens = session.getTokens(),
                        context = context
                    )
                }
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}