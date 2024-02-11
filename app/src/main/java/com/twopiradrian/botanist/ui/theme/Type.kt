package com.twopiradrian.botanist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.twopiradrian.botanist.R

val jost = FontFamily(
        Font(R.font.jost_light, FontWeight.Light),
        Font(R.font.jost_regular, FontWeight.Medium),
        Font(R.font.jost_bold, FontWeight.Bold),

)

val Typography = Typography(
        titleLarge = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Bold,
                fontSize = 42.sp,
                lineHeight = 42.sp,
                letterSpacing = 0.5.sp,
        ),
        titleMedium = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
        ),
        bodyLarge = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        bodyMedium = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        bodySmall = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelLarge = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelMedium = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = jost,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.15.sp,
        ),
)