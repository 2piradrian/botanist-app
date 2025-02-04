package com.twopiradrian.botanist.ui.components.atoms

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.twopiradrian.botanist.domain.data.Categories

@Composable
fun CategoryFilterChip (
    modifier: Modifier = Modifier,
    onClick: (Categories) -> Unit,
    categories: List<Categories>,
    category: Categories,
) {
    FilterChip(
        modifier = modifier,
        selected = categories.contains(category),
        onClick = {
            onClick(category)
        },
        label = { Text(text = stringResource(id = category.category)) },
    )
}