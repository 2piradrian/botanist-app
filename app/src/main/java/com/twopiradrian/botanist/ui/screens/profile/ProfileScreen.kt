package com.twopiradrian.botanist.ui.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.atoms.TitleLarge
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.domain.entity.PostEntity
import com.twopiradrian.botanist.ui.components.molecules.PostCard
import com.twopiradrian.botanist.ui.components.atoms.ProfileCounter
import com.twopiradrian.botanist.ui.screens.post.PostScreen

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    navigationType: NavigationType,
    contentType: ContentType
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    val scrollState = rememberLazyListState()

    val posts by viewModel.posts.collectAsState()
    val selectedPost by viewModel.selectedPost.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    val isShowingMainScreen by viewModel.isShowingMainScreen.collectAsState()

    LaunchedEffect(true) {
        viewModel.getUserProfile(session)
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
                    userProfile = userProfile,
                    posts = posts
                )
            },
            screen2 = {
                PostScreen(
                    post = selectedPost,
                    user = userProfile,
                    isPreview = false,
                    likeFunction = {},
                    followFunction = {}
                )
            },
            contentType = contentType,
            isShowingMainScreen = isShowingMainScreen
        )
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    scrollState: LazyListState,
    userProfile: UserEntity?,
    posts: List<PostEntity>,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ){
            item {
                TitleLarge(textId = R.string.profile_title)
                ProfileHeader(
                    userProfile = userProfile
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            posts.forEach {
                item {
                    PostCard(
                        onClick = {
                            viewModel.setSelectedPost(it)
                            viewModel.setIsShowingMainScreen(false) }, post = it
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    userProfile: UserEntity?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileCounter(
                modifier = Modifier.weight(1f),
                textId = R.string.profile_posts,
                count = userProfile?.posts?.size
            )
            ProfileCounter(
                modifier = Modifier.weight(1f),
                textId = R.string.profile_followers,
                count = userProfile?.followers?.size
            )
            ProfileCounter(
                modifier = Modifier.weight(1f),
                textId = R.string.profile_following,
                count = userProfile?.following?.size
            )
        }
    }
}