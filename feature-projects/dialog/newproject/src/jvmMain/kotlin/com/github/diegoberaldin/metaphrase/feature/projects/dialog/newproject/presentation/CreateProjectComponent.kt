package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

interface CreateProjectComponent :
    MviModel<CreateProjectComponent.Intent, CreateProjectComponent.UiState, CreateProjectComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Set name.
         *
         * @property value project name
         * @constructor Create [SetName]
         */
        data class SetName(val value: String) : Intent

        /**
         * Add language.
         *
         * @property value language to add
         * @constructor Create [AddLanguage]
         */
        data class AddLanguage(val value: LanguageModel) : Intent

        /**
         * Set base language.
         *
         * @property value language to mark as base
         * @constructor Create [SetBaseLanguage]
         */
        data class SetBaseLanguage(val value: LanguageModel) : Intent

        /**
         * Remove a language.
         *
         * @property value language to remove
         * @constructor Create [RemoveLanguage]
         */
        data class RemoveLanguage(val value: LanguageModel) : Intent

        /**
         * Submit the form.
         */
        object Submit : Intent
    }

    /**
     * Create project UI state.
     *
     * @property name project name
     * @property nameError error message about the name
     * @property isLoading indicates whether there is a background operation in progress
     * @property languages project languages
     * @property languagesError error message about the languages
     * @property availableLanguages available languages that can be added
     * @constructor Create [UiState]
     */
    data class UiState(
        val name: String = "",
        val nameError: String = "",
        val isLoading: Boolean = false,
        val languages: List<LanguageModel> = emptyList(),
        val languagesError: String = "",
        val availableLanguages: List<LanguageModel> = emptyList(),
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Done.
         *
         * @property projectId Project ID (null if this is an existing project being edited)
         * @constructor Create [Done]
         */
        data class Done(val projectId: Int?) : Effect
    }

    var projectId: Int
}
