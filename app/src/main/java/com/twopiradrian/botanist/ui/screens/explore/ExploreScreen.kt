package com.twopiradrian.botanist.ui.screens.explore

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.chips.CategoryFilterChip
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.components.card.PostCard
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
import com.twopiradrian.botanist.ui.screens.post.PostScreen
import kotlinx.coroutines.flow.collect

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

    val posts by viewModel.posts.collectAsState()
    val selectedPost by viewModel.selectedPost.collectAsState()
    val categories by viewModel.categoriesFlow.collectAsState()
    val isShowingMainScreen by viewModel.isShowingMainScreen.collectAsState()

    LaunchedEffect(scrollState) {
       scrollState.interactionSource.interactions.collect{
           if (scrollState.firstVisibleItemIndex == posts.size - 2) {
               viewModel.getPosts(session, categories, posts, selectedPost)
           }
       }
    }

    LaunchedEffect(categories){
        viewModel.getPosts(session, categories, emptyList(), selectedPost)
    }

    BackHandler {
        if (!isShowingMainScreen) {
            viewModel.setIsShowingMainScreen(true)
        }
    }

    AppLayout(navController = navController, navigationType = navigationType) {
        AdaptiveLayout(
            screen1 = {
                ExploreList(
                    session = session,
                    viewModel = viewModel,
                    scrollState = scrollState,
                    posts = posts,
                )
            },
            screen2 = {
                PostScreen(
                    post = selectedPost
                )
            },
            contentType = contentType,
            isShowingMainScreen = isShowingMainScreen
        )
    }
}

@Composable
fun ExploreList(
    modifier: Modifier = Modifier,
    session: Session,
    scrollState: LazyListState,
    viewModel: ExploreViewModel,
    posts: List<PostEntity>,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState,
        ){
            item {
                TitleLarge(
                    textId = R.string.explore_title
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ){
                    Categories.entries.forEach {
                        item {
                            CategoryFilterChip(
                                category = it,
                                onClick = {
                                    viewModel.setCategories(it)
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }
                }
            }
            if (posts.isEmpty()) {
                item {
                    Text(text = stringResource(id = R.string.loading))
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