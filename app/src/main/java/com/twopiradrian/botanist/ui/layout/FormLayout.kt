package com.twopiradrian.botanist.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.twopiradrian.botanist.ui.components.input.rememberImeState

@Composable
fun FormLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(imeState.value){
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = modifier.verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }

}