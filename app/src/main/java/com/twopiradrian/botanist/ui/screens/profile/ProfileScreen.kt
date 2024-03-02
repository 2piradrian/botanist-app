package com.twopiradrian.botanist.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.twopiradrian.botanist.ui.components.text.TitleLarge
import com.twopiradrian.botanist.ui.layout.AdaptiveLayout
import com.twopiradrian.botanist.ui.layout.AppLayout
import com.twopiradrian.botanist.data.datasource.app.Session
import com.twopiradrian.botanist.ui.components.text.TitleMedium

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(),
    navigationType: NavigationType,
    contentType: ContentType
) {
    val context = LocalContext.current
    val session = Session.also { it.init(context) }

    LaunchedEffect(true) {
        viewModel.getUserProfile(session)
    }

    val userProfile by viewModel.userProfile.collectAsState()

    AppLayout(navController = navController, navigationType = navigationType) {
        AdaptiveLayout(
            screen1 = {
                Body(
                    viewModel = viewModel,
                    userProfile = userProfile,
                )
            },
            screen2 = {},
            contentType = contentType,
        )
    }
}

@Composable
fun Body(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
    userProfile: UserEntity?
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TitleLarge(textId = R.string.profile_title)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "${userProfile?.posts?.size ?: 0}")
                    Text(text = "Posts")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "${userProfile?.followers?.size ?: 0}")
                    Text(text = "Followers")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "${userProfile?.following?.size ?: 0}")
                    Text(text = "Following")
                }
            }
        }

    }
}