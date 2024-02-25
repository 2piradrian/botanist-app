package com.twopiradrian.botanist.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.twopiradrian.botanist.R

@Composable
fun PostContent(
    content: String
) {
    Text(
        text = if (content == "") {
                    stringResource(id = R.string.placeholder_content)
               }
               else{
                    content
               },
        style = MaterialTheme.typography.bodyMedium
    )
}