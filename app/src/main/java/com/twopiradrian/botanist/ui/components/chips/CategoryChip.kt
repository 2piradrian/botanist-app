package com.twopiradrian.botanist.ui.components.chips

import androidx.annotation.StringRes
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CategoryChip (
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
){
    SuggestionChip(
        modifier = modifier,
        onClick = { /*TODO*/ },
        label = { Text(text = stringResource(id = textId)) },
    )
}