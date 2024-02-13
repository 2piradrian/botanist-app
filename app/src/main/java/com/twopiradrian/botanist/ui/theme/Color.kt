package com.twopiradrian.botanist.ui.theme

import androidx.compose.ui.graphics.Color

sealed class LightColors {
    companion object {
        val primary = HexToColor.getColor("#53A688")
        val secondary = HexToColor.getColor("#a66e53")
        val tertiary = HexToColor.getColor("#744d3a")
        val alternative1 = HexToColor.getColor("#5a62a6")
        val alternative2 = HexToColor.getColor("#3f4574")
        val surface = HexToColor.getColor("#F9F9F9")
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