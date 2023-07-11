package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultTranslationThemeRepository : TranslationThemeRepository {
    override val textStyle = MutableStateFlow(
        TextStyle(
            fontFamily = OpenSansFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = (0.4).sp,
        ),
    )

    override fun changeTextStyle(value: TextStyle) {
        textStyle.value = value
    }

    override fun getAvailableFontFamilies(): List<FontFamily> = listOf(
        OpenSansFontFamily,
        RobotoFontFamily,
        EbGaramondFontFamily,
        PTSerifFontFamily,
        InconsolataFontFamily,
    )

    override fun getFamilyName(index: Int): String {
        return when (index) {
            0 -> "Open Sans"
            1 -> "Roboto"
            2 -> "EB Garamond"
            3 -> "PT Serif"
            4 -> "Inconsolata"
            else -> ""
        }
    }
}
