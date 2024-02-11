package com.twopiradrian.botanist.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextButton

@Composable
fun MainButton(
    isEnabled: Boolean, @StringRes text: Int, onClick: () -> Unit
) {
    Button(
        enabled = isEnabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(shape = RoundedCornerShape(22.dp))
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
        )
    }
}

@Composable
fun PlainButton(
    modifier: Modifier = Modifier, @StringRes text: Int, onClick: () -> Unit
) {
    TextButton(onClick = onClick, modifier = modifier.padding(PaddingValues(vertical = 12.dp, horizontal = 0.dp))) {
        Text(
            text = stringResource(id = text),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}