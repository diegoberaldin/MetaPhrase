package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import kotlinx.coroutines.flow.MutableStateFlow

class DefaultThemeRepository : ThemeRepository {
    override val theme = MutableStateFlow<ThemeState>(ThemeState.Light)

    override fun changeTheme(value: ThemeState) {
        theme.value = value
    }
}
