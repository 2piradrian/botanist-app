package com.twopiradrian.botanist.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.ui.components.atoms.PostImage
import com.twopiradrian.botanist.ui.components.atoms.PostContent
import com.twopiradrian.botanist.ui.components.molecules.ConfirmAlertDialog
import com.twopiradrian.botanist.ui.components.organisms.PostFooter
import com.twopiradrian.botanist.ui.components.organisms.PostHeader

// This screen have no navController because it's used as a detail screen
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
    tabletMode: Boolean = false,
    publishButton: @Composable () -> Unit? = {},
    likeFunction: () -> Unit = {},
    followFunction: () -> Unit = {},
    deleteFunction: () -> Unit = {},
    canDelete: Boolean = false,

    // ---
    post: PostEntity?,
    user: UserEntity?
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Body(
            isPreview = isPreview,
            tabletMode = tabletMode,
            publishButton = publishButton,
            likeFunction = likeFunction,
            followFunction = followFunction,
            deleteFunction = deleteFunction,
            canDelete = canDelete,
            // ---
            post = post,
            user = user
        )
    }
}

@Composable
private fun Body(
    isPreview: Boolean = false,
    tabletMode: Boolean = false,
    publishButton: @Composable () -> Unit? = {},
    likeFunction: () -> Unit = {},
    followFunction: () -> Unit = {},
    deleteFunction: () -> Unit = {},
    canDelete: Boolean = false,
    // ---
    post: PostEntity?,
    user: UserEntity?
){
    val scrollState = rememberScrollState()
    var showConfirmAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(post) {
        scrollState.animateScrollTo(0)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            if (post == null) {
                // Do not show anything if there is no post
                return
            }
            PostHeader(
                title = post.title, description = post.description, category = post.category
            )
            Spacer(modifier = Modifier.height(12.dp))
            PostImage(
                image = post.image
            )
            Spacer(modifier = Modifier.height(12.dp))
            PostContent(
                content = post.content
            )
            Spacer(modifier = Modifier.height(12.dp))
            PostFooter(
                likeFunction = likeFunction,
                followFunction = followFunction,
                following = user?.following?.contains(post.authorId) ?: false,
                liked = user?.likes?.contains(post.id) ?: false,
                author = post.authorUsername,
            )
            if (isPreview && !tabletMode) {
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                publishButton()
            }
            Spacer(modifier = Modifier.height(38.dp))
        }
        if (canDelete) {
            FloatingActionButton(
                onClick = {
                    showConfirmAlertDialog = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_label)
                )
            }
        }
        if (showConfirmAlertDialog) {
            ConfirmAlertDialog(
                title = stringResource(id = R.string.delete_post_title),
                message = stringResource(id = R.string.delete_post_message),
                onConfirm = {
                    deleteFunction()
                    showConfirmAlertDialog = false
                },
                onDismiss = {
                    showConfirmAlertDialog = false
                }
            )
        }
    }
}