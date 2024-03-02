package com.twopiradrian.botanist.ui.components.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.twopiradrian.botanist.R

@Composable
fun PostContent(
    content: String
) {
    Text(
        text = if (content == "") stringResource(id = R.string.placeholder_content) else content ,
        style = MaterialTheme.typography.bodyMedium
    )
}