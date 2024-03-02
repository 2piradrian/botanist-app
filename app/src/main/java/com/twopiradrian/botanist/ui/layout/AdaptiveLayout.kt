package com.twopiradrian.botanist.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twopiradrian.botanist.ui.app.ContentType

@Composable
fun AdaptiveLayout(
    screen1: @Composable () -> Unit,
    screen2: @Composable (() -> Unit)? = null,
    contentType: ContentType,
    isShowingMainScreen: Boolean = true
) {
   if (screen2 != null) {
       // Two screens layout
       if (contentType == ContentType.LIST_ONLY) {
           // Single column layout
           if (isShowingMainScreen) {
               screen1()
           }
           else {
               screen2()
           }
       }
       else if (contentType == ContentType.LIST_WITH_DETAILS) {
           // Two columns layout
           Row(
                modifier = Modifier.fillMaxSize()
           ){
               Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                   screen1()
               }
               Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                   screen2()
               }
           }
       }
   }
   else {
       // Single screen layout
       screen1()
   }
}