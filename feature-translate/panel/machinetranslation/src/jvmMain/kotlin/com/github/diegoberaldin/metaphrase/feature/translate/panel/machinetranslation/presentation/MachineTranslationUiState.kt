package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation

data class MachineTranslationUiState(
    val isLoading: Boolean = false,
    val translation: String = "",
    val updateTextSwitch: Boolean = false,
)
