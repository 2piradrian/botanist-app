package com.twopiradrian.botanist.ui.components.chips

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twopiradrian.botanist.domain.data.Categories

@Composable
fun CategoryFilterChip (
    modifier: Modifier = Modifier,
    onClick: (Categories) -> Unit,
    category: Categories,
) {
    var selected by remember { mutableStateOf(false) }

    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = {
            selected = !selected
            onClick(category)
        },
        label = { Text(text = stringResource(id = category.category)) },
    )
}