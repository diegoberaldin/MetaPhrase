package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val AppFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resource = "fonts/OpenSans-Bold.ttf",
            weight = FontWeight.Bold,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-BoldItalic.ttf",
            weight = FontWeight.Bold,
            style = FontStyle.Italic,
        ),
        Font(
            resource = "fonts/OpenSans-ExtraBold.ttf",
            weight = FontWeight.ExtraBold,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-ExtraBoldItalic.ttf",
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic,
        ),
        Font(
            resource = "fonts/OpenSans-Italic.ttf",
            weight = FontWeight.Normal,
            style = FontStyle.Italic,
        ),
        Font(
            resource = "fonts/OpenSans-Light.ttf",
            weight = FontWeight.Light,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-LightItalic.ttf",
            weight = FontWeight.Light,
            style = FontStyle.Italic,
        ),
        Font(
            resource = "fonts/OpenSans-Medium.ttf",
            weight = FontWeight.Medium,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-MediumItalic.ttf",
            weight = FontWeight.Medium,
            style = FontStyle.Italic,
        ),
        Font(
            resource = "fonts/OpenSans-Regular.ttf",
            weight = FontWeight.Normal,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-SemiBold.ttf",
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal,
        ),
        Font(
            resource = "fonts/OpenSans-SemiBoldItalic.ttf",
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic,
        ),
    ),
)

val Typography = Typography(
    // h1
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp,
    ),
    // h2
    displayMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
    ),
    // h3
    displaySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        letterSpacing = 0.sp,
    ),
    // h4
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = (0.25).sp,
    ),
    // h5
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
    ),
    // h6
    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = (0.15).sp,
    ),
    // subtitle1
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (0.15).sp,
    ),
    // subtitle2
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = (0.1).sp,
    ),
    // body1
    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (0.5).sp,
    ),
    // body2
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = (0.25).sp,
    ),
    // button
    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = (0.25).sp, // original: 1.25
    ),
    // caption
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = (0.4).sp,
    ),
    // overline
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = (0.5).sp, // original: 1.5
    ),
)
