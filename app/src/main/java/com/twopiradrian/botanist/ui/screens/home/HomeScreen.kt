package com.twopiradrian.botanist.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.card.PostCard
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    AppLayout(
        navController = navController,
        adaptiveWidth = true
    ) {
        Body()
    }
}

@Composable
fun Body(){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        item{
            TitleLarge(text = R.string.home_title)
        }
        item{
            PostCard()
        }
        item{
            PostCard()
        }
        item{
            PostCard()
        }
    }
}