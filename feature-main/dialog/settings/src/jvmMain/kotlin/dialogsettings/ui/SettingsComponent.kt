package dialogsettings.ui

import projectdata.LanguageModel
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent {
    val uiState: StateFlow<SettingsUiState>

    fun setLanguage(value: LanguageModel)
    fun setSimilarity(value: String)
    fun setSpellcheckEnabled(value: Boolean)
}
