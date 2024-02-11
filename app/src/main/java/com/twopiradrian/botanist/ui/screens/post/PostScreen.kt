package com.twopiradrian.botanist.ui.screens.post

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.button.PlainButton
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.screens.home.HomeViewModel

// This screen have no navController because it's used as a detail screen
@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Body()
    }
}

@Composable
fun Body(){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        TitleLarge(
            text = "This is a post title and is very long"
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Text(
            text = LoremIpsum(20).values.joinToString(" "),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        AsyncImage(
            model = "https://uning.es/wp-content/uploads/2016/08/ef3-placeholder-image.jpg",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Text(
            text = LoremIpsum(200).values.joinToString(" "),
            style = MaterialTheme.typography.bodyMedium
        )
        PostFooter()
    }
}

@Composable
fun PostFooter(){
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(
                text = "carlitos jimenez",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "•",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "20 min ago",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row {
            PlainButton(text = R.string.post_like, onClick = {})
            Spacer(modifier = Modifier.width(8.dp))
            PlainButton(text = R.string.post_follow, onClick = {})
        }
    }
}