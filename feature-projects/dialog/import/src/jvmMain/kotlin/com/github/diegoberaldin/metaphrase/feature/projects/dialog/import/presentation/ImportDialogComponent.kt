package com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType

/**
 * Impot "all-in-one" dialog component.
 */
interface ImportDialogComponent :
    MviModel<ImportDialogComponent.Intent, ImportDialogComponent.UiState, ImportDialogComponent.Effect> {

    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Select resource file type.
         *
         * @property value resource type
         * @constructor Create [SelectType]
         */
        data class SelectType(val value: ResourceFileType) : Intent

        /**
         * Set the input path for a language.
         *
         * @property path Resource file path
         * @property lang Language of the resource file selected
         * @constructor Create [SetInputPath]
         */
        data class SetInputPath(val path: String, val lang: String) : Intent

        /**
         * Open the select file dialog for a language.
         *
         * @property lang Language of the resource file selected
         * @constructor Create [OpenFileDialog]
         */
        data class OpenFileDialog(val lang: String) : Intent

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
     * @property languages a map from language to the selected path
     * @property languagesError error message concerning the language paths
     * @property availableResourceTypes choices of resource file types
     * @property selectedResourceType selected resource file type
     * @property resourceTypeError error message concerning resource file type
     * @property isLoading flag indicating whether there is a background operation in progress
     * @constructor Create [UiState]
     */
    data class UiState(
        val languages: Map<LanguageModel, String> = emptyMap(),
        val languagesError: String = "",
        val availableResourceTypes: List<ResourceFileType> = emptyList(),
        val selectedResourceType: ResourceFileType? = null,
        val resourceTypeError: String = "",
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
         * Save file dialog to choose a source path for a language.
         */
        @Parcelize
        data class OpenFile(val lang: String) : DialogConfig
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
