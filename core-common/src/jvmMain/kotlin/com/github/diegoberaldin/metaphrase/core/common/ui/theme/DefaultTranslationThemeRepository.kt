package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultTranslationThemeRepository : TranslationThemeRepository {
    override val textStyle = MutableStateFlow(
        TextStyle(
            fontFamily = AppFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = (0.4).sp,
        ),
    )

    override fun changeTextStyle(value: TextStyle) {
        textStyle.value = value
    }
}
