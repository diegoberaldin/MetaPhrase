package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Glossary component.
 */
interface GlossaryComponent {
    /**
     * UI state
     */
    val uiState: StateFlow<GlossaryUiState>

    /**
     * Clear the section content.
     */
    fun clear()

    /**
     * Load the glossary terms for the message with a given key..
     *
     * @param key message key
     * @param projectId Project ID
     * @param languageId Language ID
     */
    fun load(key: String, projectId: Int, languageId: Int)

    /**
     * Add a source term.
     *
     * @param lemma source lemma
     */
    fun addSourceTerm(lemma: String)

    /**
     * Add a target term.
     *
     * @param lemma target lemma
     * @param source source term
     */
    fun addTargetTerm(lemma: String, source: GlossaryTermModel)

    /**
     * Delete a term.
     *
     * @param term term to delete
     */
    fun deleteTerm(term: GlossaryTermModel)
}
