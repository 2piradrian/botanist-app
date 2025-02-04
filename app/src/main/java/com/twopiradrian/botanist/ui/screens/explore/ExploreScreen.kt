package com.twopiradrian.botanist.ui.screens.explore

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.twopiradrian.botanist.core.navigation.AppScreens
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.atoms.CircularIndicator
import com.twopiradrian.botanist.ui.components.molecules.PostCard
import com.twopiradrian.botanist.ui.components.organisms.ExploreHeader
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.ui.screens.post.PostScreen

@Composable
fun ExploreScreen(
    navController: NavController,
    navigationType: NavigationType,
    contentType: ContentType,
    viewModel: ExploreViewModel = viewModel()
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val scrollState = rememberLazyListState()

    val userProfile by viewModel.userProfile.collectAsState()
    val error by viewModel.error.collectAsState()

    val posts by viewModel.posts.collectAsState()
    val page by viewModel.page.collectAsState()
    val selectedPost by viewModel.selectedPost.collectAsState()
    val categories by viewModel.categoriesFlow.collectAsState()
    val isShowingMainScreen by viewModel.isShowingMainScreen.collectAsState()

    viewModel.checkIfUserIsLoggedIn(session).also {
        if (!it) {
            navController.navigate(AppScreens.Login.route)
        }
    }

    DisposableEffect(Unit){
        onDispose {
            viewModel.resetPagination()
        }
    }

    LaunchedEffect(error) {
        if (error != 0) {
            Toast.makeText(context, context.getString(error), Toast.LENGTH_LONG).show()
        }
        viewModel.changeErrorState()
    }

    LaunchedEffect(true){
        if (userProfile == null) {
            viewModel.getUserProfile(session)
        }
    }

    LaunchedEffect(scrollState) {
       scrollState.interactionSource.interactions.collect{
           if (scrollState.firstVisibleItemIndex == posts.size - 2) {
               viewModel.getPosts(session, categories, posts, selectedPost, page)
           }
       }
    }

    LaunchedEffect(categories){
        viewModel.getPosts(session, categories, emptyList(), selectedPost, page)
    }

    BackHandler {
        if (!isShowingMainScreen) {
            viewModel.setIsShowingMainScreen(true)
        }
    }

    AppLayout(navController = navController, navigationType = navigationType) {
        AdaptiveLayout(
            screen1 = {
                Body(
                    viewModel = viewModel,
                    scrollState = scrollState,
                    categories = categories,
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
private fun Body(
    modifier: Modifier = Modifier,
    scrollState: LazyListState,
    viewModel: ExploreViewModel,
    categories: List<Categories>,
    posts: List<PostEntity>,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ){
            item {
                ExploreHeader(
                    categories = categories,
                    viewModel = viewModel
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