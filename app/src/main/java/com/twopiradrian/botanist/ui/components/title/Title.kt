package com.twopiradrian.botanist.ui.components.title

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TitleLarge(
    @StringRes text: Int
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun TitleMedium(
    @StringRes text: Int
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}