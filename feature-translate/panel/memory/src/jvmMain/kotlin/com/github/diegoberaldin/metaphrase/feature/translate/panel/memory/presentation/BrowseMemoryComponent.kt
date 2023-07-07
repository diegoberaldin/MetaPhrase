package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel

/**
 * Browse memory component.
 */
interface BrowseMemoryComponent :
    MviModel<BrowseMemoryComponent.ViewIntent, BrowseMemoryComponent.UiState, BrowseMemoryComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface ViewIntent {
        /**
         * Set the language pair.
         *
         * @param source Source language
         * @param target Target language
         */
        data class SetLanguages(val source: LanguageModel? = null, val target: LanguageModel? = null) : ViewIntent

        /**
         * Set the source language.
         *
         * @param value language to set
         */
        data class SetSourceLanguage(val value: LanguageModel?) : ViewIntent

        /**
         * Set the target language.
         *
         * @param value langauge to set
         */
        data class SetTargetLanguage(val value: LanguageModel?) : ViewIntent

        /**
         * Set the search query.
         *
         * @param value search string to set
         */
        data class SetSearch(val value: String) : ViewIntent

        /**
         * Start a search.
         */
        object OnSearchFired : ViewIntent

        /**
         * Delete an entry from the translation memory.
         *
         * @param index index of the entry to delete
         */
        data class DeleteEntry(val index: Int) : ViewIntent
    }

    /**
     * UI state from the TM content panel.
     *
     * @property sourceLanguage source language
     * @property availableSourceLanguages available source languages
     * @property targetLanguage target language
     * @property availableTargetLanguages available target languages
     * @property currentSearch search string
     * @property entries memory entries to display
     * @constructor Create [UiState]
     */
    data class UiState(
        val sourceLanguage: LanguageModel? = null,
        val availableSourceLanguages: List<LanguageModel> = emptyList(),
        val targetLanguage: LanguageModel? = null,
        val availableTargetLanguages: List<LanguageModel> = emptyList(),
        val currentSearch: String = "",
        val entries: List<TranslationMemoryEntryModel> = emptyList(),
    )

    /**
     * Effects.
     */
    sealed interface Effect
}
