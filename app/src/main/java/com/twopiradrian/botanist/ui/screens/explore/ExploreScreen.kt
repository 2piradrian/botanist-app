package com.twopiradrian.botanist.ui.screens.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.twopiradrian.botanist.ui.components.card.PostCard

@Composable
fun ExploreScreen(
    navController: NavController,
    navigationType: NavigationType,
    viewModel: ExploreViewModel = viewModel()
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val posts by viewModel.posts.collectAsState()

    LaunchedEffect(true){
        viewModel.getPosts(session)
    }

    AppLayout(navController = navController, navigationType = navigationType) {
        Body(
            session = session,
            posts = posts
        )
    }
}

@Composable
fun Body(
    session: Session,
    posts: List<PostEntity>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleLarge(
            textId = R.string.explore_title
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),

        ){
            Categories.entries.forEach {
                item {
                    CategoryFilterChip(textId = it.category, modifier = Modifier.padding(end = 4.dp))
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            posts.forEach {
                item {
                    PostCard(
                        onClick = {},
                        title = it.title,
                        description = it.description,
                        imageUrl = it.image,
                        author = it.authorUsername
                    )
                }
            }
        }
    }
}