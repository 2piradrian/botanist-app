package com.twopiradrian.botanist.ui.theme

import androidx.compose.ui.graphics.Color

sealed class LightColors {
    companion object {
        val primary = HexToColor.getColor("#53A688")
        val secondary = HexToColor.getColor("#FFF6D3")
        val tertiary = HexToColor.getColor("#E9F7FF")
        val onPrimary = HexToColor.getColor("#FFFFFF")
        val text = HexToColor.getColor("#092720")
        val error = HexToColor.getColor("#FF6D3D")
    }
}

object HexToColor {
    fun getColor(hex: String): Color {
        return Color(android.graphics.Color.parseColor(hex))
    }
}