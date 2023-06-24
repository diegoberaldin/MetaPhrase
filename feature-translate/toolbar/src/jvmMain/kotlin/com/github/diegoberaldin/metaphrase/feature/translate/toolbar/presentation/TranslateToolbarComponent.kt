package com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Translation toolbar component.
 */
interface TranslateToolbarComponent {

    /**
     * Current project ID
     */
    var projectId: Int

    /**
     * UI state
     */
    val uiState: StateFlow<TranslateToolbarUiState>

    /**
     * Event flow
     */
    val events: SharedFlow<Events>

    /**
     * Current language selected in the toolbar
     */
    val currentLanguage: StateFlow<LanguageModel?>

    /**
     * Change the current language.
     *
     * @param value language to set
     */
    fun setLanguage(value: LanguageModel)

    /**
     * Change the message filter.
     *
     * @param value filter to set
     */
    fun setTypeFilter(value: TranslationUnitTypeFilter)

    /**
     * Trigger the [Events.Search] event to be emitted in the [events] flow with the last value passed to the [setSearch] method.
     */
    fun onSearchFired()

    /**
     * Set a search query string
     *
     * @param value query to set
     */
    fun setSearch(value: String)

    /**
     * Trigger the [Events.CopyBase] event to be emitted in the [events] flow.
     */
    fun copyBase()

    /**
     * Trigger the [Events.MoveToPrevious] event to be emitted in the [events] flow.
     */
    fun moveToPrevious()

    /**
     * Trigger the [Events.MoveToNext] event to be emitted in the [events] flow.
     */
    fun moveToNext()

    /**
     * Trigger the [Events.AddUnit] event to be emitted in the [events] flow.
     */
    fun addUnit()

    /**
     * Trigger the [Events.RemoveUnit] event to be emitted in the [events] flow.
     */
    fun removeUnit()

    /**
     * Trigger the [Events.ValidateUnits] event to be emitted in the [events] flow.
     * This is intended for placeholder validation.
     */
    fun validateUnits()

    /**
     * Set the editing state.
     *
     * @param value state to set
     */
    fun setEditing(value: Boolean)

    /**
     * Events that can be emitted by the component.
     */
    sealed interface Events {
        /**
         * Move to previous segment
         */
        object MoveToPrevious : Events

        /**
         * Move to next segment
         */
        object MoveToNext : Events

        /**
         * Search in message list.
         *
         * @property text query to search
         * @constructor Create [Search]
         */
        data class Search(val text: String) : Events

        /**
         * Add new segment
         */
        object AddUnit : Events

        /**
         * Delete current segment
         */
        object RemoveUnit : Events

        /**
         * Start validation (placeholder)
         */
        object ValidateUnits : Events

        /**
         * Copy base (source) text to translation field
         */
        object CopyBase : Events
    }
}
