package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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

    /**
     * Get the available font families.
     *
     * @return available families
     */
    fun getAvailableFontFamilies(): List<FontFamily>

    /**
     * Get a readable font family name.
     *
     * @param index Index of the font family
     * @return a user-friendly name
     */
    fun getFamilyName(index: Int): String
}
