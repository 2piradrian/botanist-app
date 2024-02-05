package com.twopiradrian.botanist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.twopiradrian.botanist.R

val quickSans = FontFamily(
        Font(R.font.quicksand_light, FontWeight.Light),
        Font(R.font.quicksand_regular, FontWeight.Medium),
        Font(R.font.quicksand_semibold, FontWeight.SemiBold),
        Font(R.font.quicksand_bold, FontWeight.Bold),
)

val Typography = Typography(
        titleLarge = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
        ),
        titleMedium = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelLarge = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelMedium = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.15.sp,
        ),
        labelSmall = TextStyle(
                fontFamily = quickSans,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.15.sp,
        ),
)