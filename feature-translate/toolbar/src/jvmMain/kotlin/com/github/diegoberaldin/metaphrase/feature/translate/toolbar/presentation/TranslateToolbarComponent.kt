package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

/**
 * Translation toolbar component.
 */
interface TranslateToolbarComponent :
    MviModel<TranslateToolbarComponent.Intent, TranslateToolbarComponent.UiState, TranslateToolbarComponent.Effect> {

    sealed interface Intent {
        /**
         * Change the current language.
         *
         * @param value language to set
         */
        data class SetLanguage(val value: LanguageModel) : Intent

        /**
         * Change the message filter.
         *
         * @param value filter to set
         */
        data class SetTypeFilter(val value: TranslationUnitTypeFilter) : Intent

        /**
         * Trigger the [Effect.Search] event to be emitted in the [effects] flow with the last value passed along in the the [SetSearch] intent.
         */
        object OnSearchFired : Intent

        /**
         * Set a search query string
         *
         * @param value query to set
         */
        data class SetSearch(val value: String) : Intent

        /**
         * Trigger the [Effect.CopyBase] event to be emitted in the [effects] flow.
         */
        object CopyBase : Intent

        /**
         * Trigger the [Effect.MoveToPrevious] event to be emitted in the [effects] flow.
         */
        object MoveToPrevious : Intent

        /**
         * Trigger the [Effect.MoveToNext] event to be emitted in the [effects] flow.
         */
        object MoveToNext : Intent

        /**
         * Trigger the [Effect.AddUnit] event to be emitted in the [effects] flow.
         */
        object AddUnit : Intent

        /**
         * Trigger the [Effect.RemoveUnit] event to be emitted in the [effects] flow.
         */
        object RemoveUnit : Intent

        /**
         * Trigger the [Effect.ValidateUnits] event to be emitted in the [effects] flow.
         * This is intended for placeholder validation.
         */
        object ValidateUnits : Intent

        /**
         * Set the editing state.
         *
         * @param value state to set
         */
        data class SetEditing(val value: Boolean) : Intent
    }

    /**
     * UI state for the translation toolbar
     *
     * @property currentLanguage currently selected language
     * @property currentTypeFilter currently selected message filter
     * @property availableFilters available message filters
     * @property availableLanguages available languages
     * @property currentSearch currently selected search query
     * @property isEditing a boolean indicating whether a message is being edited
     * @constructor Create [UiState]
     */
    data class UiState(
        val currentLanguage: LanguageModel? = null,
        val currentTypeFilter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        val availableFilters: List<TranslationUnitTypeFilter> = emptyList(),
        val availableLanguages: List<LanguageModel> = emptyList(),
        val currentSearch: String = "",
        val isEditing: Boolean = false,
    )

    /**
     * Events that can be emitted by the component.
     */
    sealed interface Effect {
        /**
         * Move to previous segment
         */
        object MoveToPrevious : Effect

        /**
         * Move to next segment
         */
        object MoveToNext : Effect

        /**
         * Search in message list.
         *
         * @property text query to search
         * @constructor Create [Search]
         */
        data class Search(val text: String) : Effect

        /**
         * Add new segment
         */
        object AddUnit : Effect

        /**
         * Delete current segment
         */
        object RemoveUnit : Effect

        /**
         * Start validation (placeholder)
         */
        object ValidateUnits : Effect

        /**
         * Copy base (source) text to translation field
         */
        object CopyBase : Effect
    }

    /**
     * Current project ID
     */
    var projectId: Int
}
