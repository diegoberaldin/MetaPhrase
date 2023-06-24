package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

/**
 * Machine translation panel UI state.
 *
 * @property isLoading indication whether there is a background operation in progress
 * @property translation suggestion from MT
 * @property updateTextSwitch flag to trigger suggestion updates programmatically
 * @constructor Create [MachineTranslationUiState]
 */
data class MachineTranslationUiState(
    val isLoading: Boolean = false,
    val translation: String = "",
    val updateTextSwitch: Boolean = false,
)
