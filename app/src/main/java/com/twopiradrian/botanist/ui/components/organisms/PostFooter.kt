package com.twopiradrian.botanist.ui.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.ui.components.atoms.PlainButton
import com.twopiradrian.botanist.ui.components.molecules.PostAuthor

@Composable
fun PostFooter(
    likeFunction: () -> Unit = {},
    followFunction: () -> Unit = {},
    following: Boolean = false,
    liked: Boolean = false,
    author: String = "Author"
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PostAuthor(
            author = author
        )
        Row {
            PlainButton(
                text = if (liked) {
                        R.string.post_unlike
                    }
                    else {
                        R.string.post_like
                    },
                onClick = {
                    likeFunction()
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            PlainButton(
                text = if (following) {
                        R.string.post_unfollow
                    }
                    else {
                        R.string.post_follow
                    },
                onClick = {
                    followFunction()
                }
            )
        }
    }
}