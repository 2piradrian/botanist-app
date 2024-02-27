package com.twopiradrian.botanist.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.card.PostCard
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.screens.post.PostScreen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
    navigationType: NavigationType,
    contentType: ContentType,
) {
    val context = LocalContext.current
    val session = Session.also{ it.init(context) }

    val isShowingHomePage by viewModel.isShowingHomePage.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    LaunchedEffect(true){
        viewModel.checkIfUserIsLoggedIn(session)
    }

    LaunchedEffect(isUserLoggedIn){
        when (isUserLoggedIn) {
            true -> {
                // load some posts
            }
            false -> {
                navController.navigate(AppScreens.Login.route)
            }
        }
    }

    BackHandler {
        if (!isShowingHomePage) {
            viewModel.setIsShowingHomePage(true)
        }
    }

    AppLayout(
        navController = navController,
        navigationType = navigationType,
    ){
        Body(
            isShowingHomePage = isShowingHomePage,
            contentType = contentType,
            viewModel = viewModel
        )
    }
}

@Composable
fun Body(
    isShowingHomePage: Boolean,
    contentType: ContentType,
    viewModel: HomeViewModel
) {
    if (contentType == ContentType.LIST_ONLY) {
        if(isShowingHomePage) {
            HomeList(
                viewModel = viewModel
            )
        } else {
            PostScreen(
                // Just send the functions, not the whole viewModel
                // Probably we need create a new component with the functions implemented
            )
        }
    } else if (contentType == ContentType.LIST_WITH_DETAILS) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeList(modifier = Modifier.weight(1f), viewModel = viewModel)
            PostScreen(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item{
                TitleLarge(
                    textId = R.string.app_name
                )
                repeat(10){
                    PostCard(
                        onClick = {
                            viewModel.setIsShowingHomePage(false)
                        },
                        title = "Title",
                        description = "Description",
                        imageUrl = "https://picsum.photos/200/300",
                        author = "Author"
                    )
                }
            }
        }
    }
}