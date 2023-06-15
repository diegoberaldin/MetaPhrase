package panelmemory.presentation

import projectdata.LanguageModel
import tmdata.TranslationMemoryEntryModel

data class BrowseMemoryUiState(
    val sourceLanguage: LanguageModel? = null,
    val availableSourceLanguages: List<LanguageModel> = emptyList(),
    val targetLanguage: LanguageModel? = null,
    val availableTargetLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val entries: List<TranslationMemoryEntryModel> = emptyList(),
)
