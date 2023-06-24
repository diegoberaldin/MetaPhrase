package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

/**
 * UI state from the TM content panel.
 *
 * @property sourceLanguage source language
 * @property availableSourceLanguages available source languages
 * @property targetLanguage target language
 * @property availableTargetLanguages available target languages
 * @property currentSearch search string
 * @property entries memory entries to display
 * @constructor Create [BrowseMemoryUiState]
 */
data class BrowseMemoryUiState(
    val sourceLanguage: LanguageModel? = null,
    val availableSourceLanguages: List<LanguageModel> = emptyList(),
    val targetLanguage: LanguageModel? = null,
    val availableTargetLanguages: List<LanguageModel> = emptyList(),
    val currentSearch: String = "",
    val entries: List<TranslationMemoryEntryModel> = emptyList(),
)
