package com.twopiradrian.botanist.ui.components.title

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun TitleLarge(
    @StringRes textId: Int? = null,
    text: String? = null
) {
    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    } else {
        Text(
            text = stringResource(id = textId ?: 0),
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
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