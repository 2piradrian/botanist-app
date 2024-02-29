package com.twopiradrian.botanist.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.ui.components.image.PostImage
import com.twopiradrian.botanist.ui.components.text.PostContent
import com.twopiradrian.botanist.ui.screens.post.components.PostFooter
import com.twopiradrian.botanist.ui.screens.post.components.PostHeader

// This screen have no navController because it's used as a detail screen
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
    publishButton: @Composable () -> Unit? = {},
    likeFunction: () -> Unit = {},
    followFunction: () -> Unit = {},
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
            publishButton = publishButton,
            likeFunction = likeFunction,
            followFunction = followFunction,
            // ---
            post = post,
            user = user
        )
    }
}

@Composable
fun Body(
    isPreview: Boolean = false,
    publishButton: @Composable () -> Unit? = {},
    likeFunction: () -> Unit = {},
    followFunction: () -> Unit = {},
    // ---
    post: PostEntity?,
    user: UserEntity?
){
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        if (post == null) {
            // Do not show anything if there is no post
            return
        }
        PostHeader(
            title = post.title,
            description = post.description,
            category = post.category
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
        if(isPreview){
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            publishButton()
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}