package com.twopiradrian.botanist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.twopiradrian.botanist.core.navigation.AppNavigation
import com.twopiradrian.botanist.ui.theme.BotanistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BotanistTheme {
                AppNavigation()
            }
        }
    }
}