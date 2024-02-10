package com.twopiradrian.botanist.ui.screens.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        TitleLarge(text = "This is a post")
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "This is a very very very very very very very very very very very long description...",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }

}