package com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType

/**
 * Export "all-in-one" dialog component.
 */
interface ExportDialogComponent :
    MviModel<ExportDialogComponent.Intent, ExportDialogComponent.UiState, ExportDialogComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Add a language.
         *
         * @property value language to add
         * @constructor Create [AddLanguage]
         */
        data class AddLanguage(val value: LanguageModel) : Intent

        /**
         * Remove a language.
         *
         * @property value language to remove
         * @constructor Create [RemoveLanguage]
         */
        data class RemoveLanguage(val value: LanguageModel) : Intent

        /**
         * Select resource file type.
         *
         * @property value resource type
         * @constructor Create [SelectType]
         */
        data class SelectType(val value: ResourceFileType) : Intent

        /**
         * Set the output path (must be a zip file).
         *
         * @property value path to write the messages.
         * @constructor Create [SetOutputPath]
         */
        data class SetOutputPath(val value: String) : Intent

        /**
         * Open a dialog to select a destination path.
         */
        object OpenFileDialog : Intent

        /**
         * Close any currently open dialog.
         */
        object CloseDialog : Intent

        /**
         * Submit the data.
         */
        object Submit : Intent
    }

    /**
     * UI state.
     *
     * @property availableLanguages project languages
     * @property selectedLanguages selected languages to export
     * @property languagesError error message concerning languages
     * @property availableResourceTypes choices of resource file types
     * @property selectedResourceType selected resource file type
     * @property resourceTypeError error message concerning resource file type
     * @property outputPath destination path (must be a .zip file)
     * @property outputPathError error message concerning the destination path
     * @property isLoading flag indicating whether there is a background operation in progress
     * @constructor Create [UiState]
     */
    data class UiState(
        val availableLanguages: List<LanguageModel> = emptyList(),
        val selectedLanguages: List<LanguageModel> = emptyList(),
        val languagesError: String = "",
        val availableResourceTypes: List<ResourceFileType> = emptyList(),
        val selectedResourceType: ResourceFileType? = null,
        val resourceTypeError: String = "",
        val outputPath: String = "",
        val outputPathError: String = "",
        val isLoading: Boolean = false,
    )

    /**
     * Effects.
     */
    sealed interface Effect {
        object Done : Effect
    }

    /**
     * Sub-dialog configurations.
     */
    sealed interface DialogConfig : Parcelable {
        /**
         * None.
         */
        @Parcelize
        object None : DialogConfig

        /**
         * Save file dialog to choose destination path.
         */
        @Parcelize
        object SelectOutputFile : DialogConfig
    }

    /**
     * ID of the project to export.
     */
    var projectId: Int

    /**
     * Dialog navigation slot.
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>
}
