package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Browse memory component.
 */
interface BrowseMemoryComponent {
    /**
     * UI state
     */
    val uiState: StateFlow<BrowseMemoryUiState>

    /**
     * Set the language pair.
     *
     * @param source Source language
     * @param target Target language
     */
    fun setLanguages(source: LanguageModel? = null, target: LanguageModel? = null)

    /**
     * Set the source language.
     *
     * @param value language to set
     */
    fun setSourceLanguage(value: LanguageModel?)

    /**
     * Set the target language.
     *
     * @param value langauge to set
     */
    fun setTargetLanguage(value: LanguageModel?)

    /**
     * Set the search query.
     *
     * @param value search string to set
     */
    fun setSearch(value: String)

    /**
     * Start a search.
     */
    fun onSearchFired()

    /**
     * Delete an entry from the translation memory.
     *
     * @param index index of the entry to delete
     */
    fun deleteEntry(index: Int)
}
