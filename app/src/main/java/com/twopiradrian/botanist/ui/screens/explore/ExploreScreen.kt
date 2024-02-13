package com.twopiradrian.botanist.ui.screens.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.twopiradrian.botanist.R
import com.twopiradrian.botanist.domain.data.Categories
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.components.chips.CategoryChip
import com.twopiradrian.botanist.ui.components.chips.CategoryFilterChip
import com.twopiradrian.botanist.ui.components.title.TitleLarge
import com.twopiradrian.botanist.ui.layout.AppLayout

@Composable
fun ExploreScreen(
    navController: NavController,
    navigationType: NavigationType
) {
    AppLayout(navController = navController, navigationType = navigationType) {
        Body()
    }
}

@Composable
fun Body() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleLarge(
            textId = R.string.explore_title
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),

        ){
            Categories.entries.forEach {
                item {
                    CategoryFilterChip(textId = it.category, modifier = Modifier.padding(end = 4.dp))
                }
            }
        }
    }
}