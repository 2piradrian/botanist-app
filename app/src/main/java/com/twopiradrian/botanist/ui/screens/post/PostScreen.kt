package com.twopiradrian.botanist.ui.screens.post

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    // ---
    title: String = "",
    description: String = "",
    category: String = "",
    image: Uri? = null,
    content: String = ""
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Body(
            isPreview = isPreview,
            publishButton = publishButton,
            // ---
            title = title,
            description = description,
            category = category,
            image = image,
            content = content
        )
    }
}

@Composable
fun Body(
    isPreview: Boolean = false,
    publishButton: @Composable () -> Unit? = {},
    // ---
    title: String,
    description: String,
    category: String,
    image: Uri?,
    content: String
){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        PostHeader(
            title = title,
            description = description,
            category = category
        )
        Spacer(modifier = Modifier.height(12.dp))
        PostImage(
            image = image,
        )
        Spacer(modifier = Modifier.height(12.dp))
        PostContent(
            content = content
        )
        Spacer(modifier = Modifier.height(12.dp))
        PostFooter(
            likeFunction = {},
            followFunction = {},
            following = false,
            liked = false,
            author = "Author"
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