package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.ui.text.font.FontFamily
import kotlinx.coroutines.flow.MutableStateFlow

class DefaultThemeRepository : ThemeRepository {

    override val theme = MutableStateFlow<ThemeState>(ThemeState.Light)

    override val fontFamily = MutableStateFlow(OpenSansFontFamily)

    override fun changeTheme(value: ThemeState) {
        theme.value = value
    }

    override fun changeFontFamily(family: FontFamily) {
        fontFamily.value = family
    }
}
