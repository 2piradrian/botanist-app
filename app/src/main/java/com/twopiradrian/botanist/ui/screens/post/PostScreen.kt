package com.twopiradrian.botanist.ui.screens.post

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.components.button.PlainButton
import com.twopiradrian.botanist.ui.components.image.PostImage
import com.twopiradrian.botanist.ui.components.text.PostAuthor
import com.twopiradrian.botanist.ui.components.text.PostContent
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.screens.home.HomeViewModel

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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        PostHeader(
            title = title,
            description = description,
            category = category
        )
        Spacer(modifier = Modifier.height(12.dp))
        PostImage()
        Spacer(modifier = Modifier.height(12.dp))
        PostContent()
        Spacer(modifier = Modifier.height(12.dp))
        PostFooter()
        if(isPreview){
            Spacer(modifier = Modifier.height(12.dp))
            publishButton()
        }
        Spacer(modifier = Modifier.height(12.dp))

    }
}

@Composable
fun PostHeader(
    title: String,
    description: String,
    category: String
){
    TitleLarge(
        text = if (title == "") {
            stringResource(id = R.string.placeholder_title)
        }
        else {
            title
        },
    )
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Text(
        text = if (description == "") {
            stringResource(id = R.string.placeholder_description)
        } else {
            description
        },
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.bodyMedium,
    )
    Spacer(
        modifier = Modifier.height(12.dp)
    )
    Row{
        Text(
            text = stringResource(id = R.string.post_category),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (category == "") {
                stringResource(id = R.string.placeholder_category)
            } else {
                stringResource(id = Categories.valueOf(category).category)
            },
            style = MaterialTheme.typography.bodySmall
        )
    }

}

@Composable
fun PostFooter(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostAuthor()
        Row {
            PlainButton(text = R.string.post_like, onClick = {})
            Spacer(modifier = Modifier.width(8.dp))
            PlainButton(text = R.string.post_follow, onClick = {})
        }
    }
}