package com.twopiradrian.botanist.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.card.PostCard
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.screens.post.PostScreen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    navigationType: NavigationType,
) {

    val isShowingHomePage by viewModel.isShowingHomePage.collectAsState()

    BackHandler {
        if (!isShowingHomePage) {
            viewModel.setIsShowingHomePage(true)
        }
    }

    AppLayout(
        navController = navController,
        navigationType = navigationType,
        adaptiveWidth = true,
    ) {
        Body(
            isShowingHomePage = isShowingHomePage
        )
    }
}

@Composable
fun Body(
    isShowingHomePage: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        if (isShowingHomePage) {
            item{
                TitleLarge(text = R.string.home_title)
                PostCard()
                PostCard()
                PostCard()
            }
        }else{
            item{
                PostScreen(

                )
            }
        }
    }
}