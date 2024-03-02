package com.twopiradrian.botanist.ui.components.atoms

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlainButton(
    modifier: Modifier = Modifier, @StringRes text: Int, onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.padding(
            PaddingValues(
                vertical = 12.dp,
                horizontal = 0.dp
            )
        )
    ) {
        Text(
            text = stringResource(id = text),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp
            ),
        )
    }
}