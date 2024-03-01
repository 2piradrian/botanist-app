package com.twopiradrian.botanist.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.card.PostCard
import com.twopiradrian.botanist.ui.components.loading.CircularIndicator
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
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

    val scrollState = rememberLazyListState()

    val userProfile by viewModel.userProfile.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    val posts by viewModel.posts.collectAsState()
    val selectedPost by viewModel.selectedPost.collectAsState()
    val isShowingMainScreen by viewModel.isShowingMainScreen.collectAsState()

    LaunchedEffect(true){
        viewModel.checkIfUserIsLoggedIn(session)
    }

    LaunchedEffect(isUserLoggedIn){
        if (!isUserLoggedIn) return@LaunchedEffect

        viewModel.getUserProfile(session)
    }

    LaunchedEffect(scrollState, isUserLoggedIn) {
        if (!isUserLoggedIn) return@LaunchedEffect

        scrollState.interactionSource.interactions.collect{
            if (scrollState.firstVisibleItemIndex == posts.size - 2) {
                viewModel.getPosts(session, posts, selectedPost)
            }
        }
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
        if (!isShowingMainScreen) {
            viewModel.setIsShowingMainScreen(true)
        }
    }

    AppLayout(
        navController = navController,
        navigationType = navigationType,
    ){
        AdaptiveLayout(
            screen1 = {
                HomeList(
                    viewModel = viewModel,
                    posts = posts,
                )
            },
            screen2 = {
                PostScreen(
                    post = selectedPost,
                    user = userProfile,
                    isPreview = false,
                    likeFunction = {
                        selectedPost?.let {
                            viewModel.likePost(session, it, userProfile)
                        }
                    },
                    followFunction = {
                        selectedPost?.let {
                            viewModel.followUser(session, it, userProfile)
                        }
                    }
                )
            },
            contentType = contentType,
            isShowingMainScreen = isShowingMainScreen
        )
    }
}

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    posts: List<PostEntity>,
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
            }
            if (posts.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                    CircularIndicator(modifier = Modifier.fillMaxSize())
                }
            }
            else {
                posts.forEach {
                    item {
                        PostCard(
                            onClick = {
                                viewModel.setSelectedPost(it)
                                viewModel.setIsShowingMainScreen(false)
                            },
                            post = it
                        )
                    }
                }
            }
        }
    }
}