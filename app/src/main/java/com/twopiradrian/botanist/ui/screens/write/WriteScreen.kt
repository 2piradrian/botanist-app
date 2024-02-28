package com.twopiradrian.botanist.ui.screens.write

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.twopiradrian.botanist.ui.app.InputData
import com.twopiradrian.botanist.ui.app.InputType
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.components.button.ImagePickerButton
import com.twopiradrian.botanist.ui.components.button.SecondaryButton
import com.twopiradrian.botanist.ui.app.ImageData
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
import com.twopiradrian.botanist.ui.layout.FormLayout
import com.twopiradrian.botanist.ui.screens.post.PostScreen
import java.util.Date

@Composable
fun WriteScreen(
    navController: NavController,
    navigationType: NavigationType,
    contentType: ContentType,
    viewModel: WriteViewModel = viewModel()
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val isShowingThePost by viewModel.isShowingThePost.collectAsState()
    val postedSuccessfully by viewModel.postedSuccessfully.collectAsState()

    val titleInput by viewModel.title.collectAsState()
    val descriptionInput by viewModel.description.collectAsState()
    val categoryInput by viewModel.category.collectAsState()
    val imageInput by viewModel.image.collectAsState()
    val contentInput by viewModel.content.collectAsState()

    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    BackHandler {
        if (isShowingThePost) {
            viewModel.setIsShowingThePost(false)
        }
    }

    LaunchedEffect(postedSuccessfully) {
        if (postedSuccessfully) {
            navController.navigate(AppScreens.Home.route)
        }
    }

    AppLayout(navController = navController, navigationType = navigationType) {
        AdaptiveLayout(
            screen1 = {
                PostForm(
                    viewModel = viewModel,
                    context = context,
                    session = session,
                    // --
                    titleInput = titleInput,
                    descriptionInput = descriptionInput,
                    categoryInput = categoryInput,
                    imageInput = imageInput,
                    contentInput = contentInput,
                    isButtonEnabled = isButtonEnabled,
                    contentType = contentType
                )
            },
            screen2 = {
                PostScreen(
                    isPreview = true,
                    publishButton = {
                        SecondaryButton(
                            isEnabled = isButtonEnabled,
                            text = R.string.write_post_button,
                            onClick = {
                                viewModel.createPost(
                                    title = titleInput.state,
                                    description = descriptionInput.state,
                                    category = categoryInput.state,
                                    content = contentInput.state,
                                    image = imageInput.state,
                                    tokens = session.getTokens(),
                                    context = context
                                )
                            }
                        )
                    },
                    // --
                    post = PostEntity(
                        title = titleInput.state ,
                        description = descriptionInput.state,
                        category = categoryInput.state,
                        content = contentInput.state,
                        image = imageInput.state.toString(),
                        authorUsername = "Author",
                        authorId = "0",
                        id = "0",
                        createdAt = Date(),
                        likedBy = listOf(),
                    )
                )
            },
            contentType = contentType,
            isShowingMainScreen = !isShowingThePost
        )
    }
}

@Composable
fun PostForm(
    modifier: Modifier = Modifier,
    viewModel: WriteViewModel,
    context: Context,
    session: Session,
    contentType: ContentType,
    // --
    titleInput: InputData,
    descriptionInput: InputData,
    categoryInput: InputData,
    imageInput: ImageData,
    contentInput: InputData,
    isButtonEnabled: Boolean,

    ) {
    Column(
        modifier = modifier.fillMaxSize()
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
                        image = imageInput.state,
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
                        image = imageInput.state,
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
                        image = imageInput.state,
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
                        image = imageInput.state,
                        content = it
                    )
                },
                maxLines = 200,
                singleLine = false,
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            if (contentType == ContentType.LIST_WITH_DETAILS) {
                SecondaryButton(
                    isEnabled = isButtonEnabled,
                    text = R.string.write_post_button,
                    onClick = {
                        viewModel.createPost(
                            title = titleInput.state,
                            description = descriptionInput.state,
                            category = categoryInput.state,
                            content = contentInput.state,
                            image = imageInput.state,
                            tokens = session.getTokens(),
                            context = context
                        )
                    }
                )
            }
            else {
                SecondaryButton(
                    onClick = {
                        viewModel.setIsShowingThePost(true)
                    },
                    text = R.string.write_post_preview)
            }
            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}