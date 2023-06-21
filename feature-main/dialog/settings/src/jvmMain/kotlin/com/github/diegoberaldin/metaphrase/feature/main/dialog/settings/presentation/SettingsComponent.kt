package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {
    val uiState: StateFlow<SettingsUiState>
    val languageUiState: StateFlow<SettingsLanguageUiState>
    val machineTranslationUiState: StateFlow<SettingsMachineTranslationUiState>

    fun setLanguage(value: LanguageModel)
    fun setSimilarity(value: String)
    fun setSpellcheckEnabled(value: Boolean)
    fun setMachineTranslationProvider(index: Int)
    fun setMachineTranslationKey(value: String)
}
