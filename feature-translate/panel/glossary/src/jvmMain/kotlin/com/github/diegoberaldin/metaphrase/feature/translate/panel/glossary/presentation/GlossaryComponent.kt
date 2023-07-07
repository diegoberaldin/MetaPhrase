package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

/**
 * Glossary component.
 */
interface GlossaryComponent :
    MviModel<GlossaryComponent.Intent, GlossaryComponent.UiState, GlossaryComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Clear the section content.
         */
        object Clear : Intent

        /**
         * Load the glossary terms for the message with a given key..
         *
         * @param key message key
         * @param projectId Project ID
         * @param languageId Language ID
         */
        data class Load(val key: String, val projectId: Int, val languageId: Int) : Intent

        /**
         * Add a source term.
         *
         * @param lemma source lemma
         */
        data class AddSourceTerm(val lemma: String) : Intent

        /**
         * Add a target term.
         *
         * @param lemma target lemma
         * @param source source term
         */
        data class AddTargetTerm(val lemma: String, val source: GlossaryTermModel) : Intent

        /**
         * Delete a term.
         *
         * @param term term to delete
         */
        data class DeleteTerm(val term: GlossaryTermModel) : Intent
    }

    /**
     * Glossary panel UI state.
     *
     * @property sourceFlag flag of the source language
     * @property targetFlag flag of the target language
     * @property isLoading indicating whether there is a background operation in progress
     * @property isBaseLanguage indicating whether this is the base (deprecated) glossary panel, now it is displayed only for target languages
     * @property terms terms to show (1 source term for _n_ target terms)
     * @constructor Create [UiState]
     */
    data class UiState(
        val sourceFlag: String = "",
        val targetFlag: String = "",
        val isLoading: Boolean = false,
        val isBaseLanguage: Boolean = false,
        val terms: List<Pair<GlossaryTermModel, List<GlossaryTermModel>>> = emptyList(),
    )

    /**
     * Effects.
     */
    sealed interface Effect
}
