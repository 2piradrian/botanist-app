package com.twopiradrian.botanist.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

@Composable
fun PostContent() {
    Text(
        text = LoremIpsum(200).values.joinToString(" "),
        style = MaterialTheme.typography.bodyMedium
    )
}