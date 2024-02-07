package com.twopiradrian.botanist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.error
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.onPrimary
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.primary
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.secondary
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.surface
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.tertiary
import com.twopiradrian.botanist.ui.theme.LightColors.Companion.text

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    tertiary = tertiary,
    surface = surface,
    onBackground = text,
    error = error
)

@Composable
fun BotanistTheme(
        darkTheme: Boolean = false,
        dynamicColor: Boolean = false,
        content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
    )
}