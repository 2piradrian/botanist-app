package com.twopiradrian.botanist.ui.components.chips

import androidx.annotation.StringRes
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CategoryFilterChip (
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @StringRes textId: Int,
){
    //SuggestionChip(
    //    modifier = modifier,
    //    onClick = { onClick() },
    //    label = { Text(text = stringResource(id = textId)) },
    //)

    var selected by remember { mutableStateOf(false) }

    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = {
            onClick()
            selected = !selected
        },
        label = { Text(text = stringResource(id = textId)) },
    )
}

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @StringRes textId: Int,
){
    SuggestionChip(
        modifier = modifier,
        onClick = { onClick() },
        label = { Text(text = stringResource(id = textId)) },
    )
}