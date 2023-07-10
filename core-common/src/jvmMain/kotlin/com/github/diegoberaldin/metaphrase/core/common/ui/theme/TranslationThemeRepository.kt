package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.flow.StateFlow

/**
 * A single global repository for the translation UI theme.
 */
interface TranslationThemeRepository {

    /**
     * Observable text style for text fields.
     */
    val textStyle: StateFlow<TextStyle>

    /**
     * Change the text style.
     *
     * @param value Value to set
     */
    fun changeTextStyle(value: TextStyle)
}
