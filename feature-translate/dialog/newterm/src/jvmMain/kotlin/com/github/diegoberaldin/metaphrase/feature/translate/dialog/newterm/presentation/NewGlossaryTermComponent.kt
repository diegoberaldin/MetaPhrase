package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Glossary term pair.
 *
 * @property sourceLemma lemma in the source language
 * @property targetLemma lemma in the target language
 * @constructor Create [GlossaryTermPair]
 */
data class GlossaryTermPair(
    val sourceLemma: String,
    val targetLemma: String,
)

/**
 * New glossary term component.
 */
interface NewGlossaryTermComponent {
    /**
     * UI state
     */
    val uiState: StateFlow<NewGlossaryTermUiState>

    /**
     * Event emitted after successful submission.
     */
    val done: SharedFlow<GlossaryTermPair>

    /**
     * Set source term.
     *
     * @param value term to set
     */
    fun setSourceTerm(value: String)

    /**
     * Set target term.
     *
     * @param value term to set
     */
    fun setTargetTerm(value: String)

    /**
     * Confirm the inserted term pair..
     */
    fun submit()
}
