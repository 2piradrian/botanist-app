package com.twopiradrian.botanist.ui.components.organisms

import androidx.compose.runtime.Composable
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.components.atoms.TitleLarge
import com.twopiradrian.botanist.ui.components.molecules.ExploreFilters
import com.twopiradrian.botanist.ui.screens.explore.ExploreViewModel

@Composable
fun ExploreHeader(
    categories: List<Categories>,
    viewModel: ExploreViewModel
) {
    TitleLarge(
        textId = R.string.explore_title
    )
    ExploreFilters(
        categories = categories,
        onClick = {
            viewModel.setCategories(it)
        }
    )
}