package com.twopiradrian.botanist.ui.theme

import androidx.compose.ui.graphics.Color

sealed class LightColors {
    companion object {
        val primary = HexToColor.getColor("#53A688")
        val secondary = HexToColor.getColor("#C4DFAA")
        val tertiary = HexToColor.getColor("#E1F6FF")
        val text = HexToColor.getColor("#092720")
        val error = HexToColor.getColor("#FF6D3D")
    }
}

object HexToColor {
    fun getColor(hex: String): Color {
        return Color(android.graphics.Color.parseColor(hex))
    }
}