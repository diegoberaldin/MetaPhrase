package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

data class BrowseMemoryUiState(
    val sourceLanguage: LanguageModel? = null,
    val availableSourceLanguages: List<LanguageModel> = emptyList(),
    val targetLanguage: LanguageModel? = null,
    val availableTargetLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val entries: List<TranslationMemoryEntryModel> = emptyList(),
)
