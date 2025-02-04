package com.twopiradrian.botanist.ui.components.molecules

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.components.atoms.CategoryFilterChip

@Composable
fun ExploreFilters(
    categories: List<Categories>,
    onClick: (Categories) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ){
        Categories.entries.forEach {
            item {
                CategoryFilterChip(
                    category = it,
                    categories = categories,
                    onClick = {
                        onClick(it)
                    },
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}