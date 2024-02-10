@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.twopiradrian.botanist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.twopiradrian.botanist.core.navigation.AppNavigation
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.theme.BotanistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            BotanistTheme {
                val windowSize = calculateWindowSizeClass(this)

                val navigationType = when(windowSize.widthSizeClass){
                    WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAVIGATION
                    WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
                    WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
                    else -> NavigationType.BOTTOM_NAVIGATION
                }

                AppNavigation(
                    navigationType = navigationType
                )
            }
        }
    }
}