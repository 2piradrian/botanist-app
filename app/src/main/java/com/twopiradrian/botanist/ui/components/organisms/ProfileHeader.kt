package com.twopiradrian.botanist.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.entity.UserEntity
import com.twopiradrian.botanist.ui.components.atoms.ProfileCounter

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