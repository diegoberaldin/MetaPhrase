package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation

import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel

interface AppearanceSettingsComponent :
    MviModel<AppearanceSettingsComponent.Intent, AppearanceSettingsComponent.UiState, AppearanceSettingsComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Enable/disable the dark UI theme.
         *
         * @property value true to enable dark mode, false to use light mode
         * @constructor Create [SetDarkMode]
         */
        data class SetDarkMode(val value: Boolean) : Intent

        /**
         * Set the font size used in the translation editor.
         *
         * @property value font size to set
         * @constructor Create [SetEditorFontSize]
         */
        data class SetEditorFontSize(val value: Int) : Intent
    }

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * UI state for appearance settings.
     *
     * @property isLoading true if there is a background operation in progress
     * @property darkModeEnabled true if dark UI theme is enabled
     * @property editorFontSize font size used in the translation editor
     * @constructor Create [UiState]
     */
    data class UiState(
        val isLoading: Boolean = false,
        val darkModeEnabled: Boolean = false,
        val editorFontSize: String = "",
    )
}
