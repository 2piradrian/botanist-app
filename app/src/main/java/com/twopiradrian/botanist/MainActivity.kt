@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.twopiradrian.botanist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.twopiradrian.botanist.core.navigation.AppNavigation
import com.twopiradrian.botanist.ui.app.ContentType
import com.twopiradrian.botanist.ui.app.NavigationType
import com.twopiradrian.botanist.ui.theme.BotanistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            BotanistTheme {
                val windowSize = calculateWindowSizeClass(this)

                val navigationType: NavigationType
                val contentType: ContentType

                when(windowSize.widthSizeClass){
                    WindowWidthSizeClass.Compact -> {
                        navigationType = NavigationType.BOTTOM_NAVIGATION
                        contentType = ContentType.LIST_ONLY
                    }
                    WindowWidthSizeClass.Medium -> {
                        navigationType = NavigationType.NAVIGATION_RAIL
                        contentType = ContentType.LIST_ONLY
                    }
                    WindowWidthSizeClass.Expanded -> {
                        navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
                        contentType = ContentType.LIST_WITH_DETAILS
                    }
                    else -> {
                        navigationType = NavigationType.BOTTOM_NAVIGATION
                        contentType = ContentType.LIST_ONLY
                    }
                }

                AppNavigation(
                    navigationType = navigationType,
                    contentType = contentType
                )
            }
        }
    }
}