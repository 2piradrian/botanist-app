package com.twopiradrian.botanist.ui.components.atoms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp

@Composable
fun AnimatedError(
    visible: Boolean,
    errorState: Int,
    paddingStart: Dp
) {
    AnimatedVisibility(visible = visible) {
        Text(
            text = if (visible) stringResource(errorState) else "",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(start = paddingStart),
            style = MaterialTheme.typography.labelSmall
        )
    }
}