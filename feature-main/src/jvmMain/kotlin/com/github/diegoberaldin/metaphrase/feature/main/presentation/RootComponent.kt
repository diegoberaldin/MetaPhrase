package com.github.diegoberaldin.metaphrase.feature.main.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType

/**
 * Root component.
 */
interface RootComponent : MviModel<RootComponent.Intent, RootComponent.UiState, RootComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Open the "Open project" dialog.
         */
        object OpenDialog : Intent

        /**
         * Loads a translation project from disk.
         *
         * @param path path of the TMX file
         */
        data class OpenProject(val path: String) : Intent

        /**
         * Open the "Edit project" dialog.
         */
        object OpenEditProject : Intent

        /**
         * Save the current project in the TMX at the specified path.
         */
        object SaveCurrentProject : Intent

        /**
         * Opens the "Save as" project dialog.
         */
        object SaveProjectAs : Intent

        /**
         * Save the project as a TMX file at the given path.
         *
         * @param path path of the TMX to write
         */
        data class SaveProject(val path: String) : Intent

        /**
         * Open the "New project" dialog.
         */
        object OpenNewDialog : Intent

        /**
         * Close any opened dialog.
         */
        object CloseDialog : Intent

        /**
         * Displays the "Confirm project close" dialog.
         *
         * @param closeAfter if set to true, after confirmation the application will exit
         */
        data class CloseCurrentProject(val closeAfter: Boolean = false) : Intent

        /**
         * Sends user confirmation to close the current project with an optional action afterwards.
         *
         * @param openAfter if set to true, after closing the "Open project" dialog will be opened
         * @param newAfter if set to true, after closing the "New project" dialog will be opened
         */
        data class ConfirmCloseCurrentProject(val openAfter: Boolean = false, val newAfter: Boolean = false) :
            Intent

        /**
         * Open the "Statistics" dialog.
         */
        object OpenStatistics : Intent

        /**
         * Open the "Settings" dialog.
         */
        object OpenSettings : Intent

        /**
         * Open the import dialog for the current language.
         *
         * @param type resource file type
         */
        data class OpenImportDialog(val type: ResourceFileType) : Intent

        /**
         * Open the export dialog for the current language.
         *
         * @param type resource file type
         */
        data class OpenExportDialog(val type: ResourceFileType) : Intent

        /**
         * Import messages from a resource file.
         *
         * @param path file path
         * @param type resource type
         */
        data class Import(val path: String, val type: ResourceFileType) : Intent

        /**
         * Export messages to a resource file.
         *
         * @param path file path
         * @param type resource type
         */
        data class Export(val path: String, val type: ResourceFileType) : Intent

        /**
         * Navigate to the previous segment in the editor.
         */
        object MoveToPreviousSegment : Intent

        /**
         * Navigate to the next segment in the editor.
         */
        object MoveToNextSegment : Intent

        /**
         * End editing the current message.
         */
        object EndEditing : Intent

        /**
         * Copy the base (source) message to the target message.
         */
        object CopyBase : Intent

        /**
         * Open the "New segment" dialog.
         */
        object AddSegment : Intent

        /**
         * Delete the current segment.
         */
        object DeleteSegment : Intent

        /**
         * Open the dialog to export the current TM content to TMX.
         */
        object OpenExportTmxDialog : Intent

        /**
         * Export the current TM content to a TMX file.
         *
         * @param path TMX file path
         */
        data class ExportTmx(val path: String) : Intent

        /**
         * Open the import dialog to populate the TM.
         */
        object OpenImportTmxDialog : Intent

        /**
         * Import a TMX file to the translation memory.
         *
         * @param path file path
         */
        data class ImportTmx(val path: String) : Intent

        /**
         * Clear the content of the global Translation Memory.
         */
        object ClearTm : Intent

        /**
         * Import all the messages of the current project into the global TM.
         */
        object SyncTm : Intent

        /**
         * Starts the placeholder validation.
         */
        object ValidatePlaceholders : Intent

        /**
         * Insert the best TM match to the translation editor.
         */
        object InsertBestMatch : Intent

        /**
         * Start a global spellcheck validation.
         */
        object GlobalSpellcheck : Intent

        /**
         * Open the import glossary dialog.
         */
        object OpenImportGlossaryDialog : Intent

        /**
         * Import a CSV file into the glossary.
         *
         * @param path file path
         */
        data class ImportGlossary(val path: String) : Intent

        /**
         * Open the export glossary dialog.
         */
        object OpenExportGlossaryDialog : Intent

        /**
         * Export the global glossary to a CSV file.
         *
         * @param path file path
         */
        data class ExportGlossary(val path: String) : Intent

        /**
         * Clear the global glossary.
         */
        object ClearGlossary : Intent

        /**
         * Retrieve a suggestion for the current message from the machine translation provider.
         */
        object MachineTranslationRetrieve : Intent

        /**
         * Insert the current suggestion from machine translation in the editor.
         */
        object MachineTranslationInsert : Intent

        /**
         * Copy the target message into the machine translation suggestion.
         */
        object MachineTranslationCopyTarget : Intent

        /**
         * Share the current suggestion to the remote machine translation provider.
         */
        object MachineTranslationShare : Intent

        /**
         * Share the whole project as a TM to the machine translation provider.
         */
        object MachineTranslationContributeTm : Intent

        /**
         * Open the user manual.
         */
        object OpenManual : Intent
    }

    /**
     * Effects.
     */
    sealed interface Effect

    /**
     * Root UI state.
     *
     * @property activeProject project currently opened
     * @property isEditing flag indicating whether any message is being edited
     * @property currentLanguage current language selected in the toolbar
     * @property isLoading flag indicating whether there is an operation running in the background
     * @property isSaveEnabled flag indicating whether the save menu action should be enabled
     * @constructor Create [UiState]
     */
    data class UiState(
        val activeProject: ProjectModel? = null,
        val isEditing: Boolean = false,
        val currentLanguage: LanguageModel? = null,
        val isLoading: Boolean = false,
        val isSaveEnabled: Boolean = false,
    )

    /**
     * Navigation slot for the main content.
     */
    val main: Value<ChildSlot<Config, *>>

    /**
     * Navigation slot for currently opened dialog.
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>

    /**
     * Returns whether the current project has any unsaved changes.
     *
     * @return true if there are unsaved changed
     */
    fun hasUnsavedChanges(): Boolean

    /**
     * Main screen content slot configuration.
     */
    sealed interface Config : Parcelable {
        /**
         * Intro (welcome) screen.
         */
        @Parcelize
        object Intro : Config

        /**
         * Project list or translation editor.
         */
        @Parcelize
        object Projects : Config
    }

    /**
     * Available dialog configurations.
     */
    sealed interface DialogConfig : Parcelable {
        /**
         * No dialog (close dialog)
         */
        @Parcelize
        object None : DialogConfig

        /**
         * Open project dialog
         */
        @Parcelize
        object OpenDialog : DialogConfig

        /**
         * New project dialog
         */
        @Parcelize
        object NewDialog : DialogConfig

        /**
         * Project edit dialog
         */
        @Parcelize
        object EditDialog : DialogConfig

        /**
         * Save project dialog (aka "Save as").
         *
         * @property name
         * @constructor Create [SaveAsDialog]
         */
        @Parcelize
        data class SaveAsDialog(val name: String) : DialogConfig

        /**
         * Confirm close dialog if there are unsaved changes before closing the application or opening a new project.
         *
         * @property closeAfter
         * @property openAfter
         * @property newAfter
         * @constructor Create [ConfirmCloseDialog]
         */
        @Parcelize
        data class ConfirmCloseDialog(
            val closeAfter: Boolean = false,
            val openAfter: Boolean = false,
            val newAfter: Boolean = false,
        ) : DialogConfig

        /**
         * Import messages dialog.
         *
         * @property type resource type
         * @constructor Create [ImportDialog]
         */
        @Parcelize
        data class ImportDialog(val type: ResourceFileType) : DialogConfig

        /**
         * Export messages dialog.
         *
         * @property type resource type
         * @constructor Create [ExportDialog]
         */
        @Parcelize
        data class ExportDialog(val type: ResourceFileType) : DialogConfig

        /**
         * Statistics dialog.
         */
        @Parcelize
        object StatisticsDialog : DialogConfig

        /**
         * Settings dialog.
         */
        @Parcelize
        object SettingsDialog : DialogConfig

        /**
         * Export TM dialog.
         */
        @Parcelize
        object ExportTmxDialog : DialogConfig

        /**
         * Import glossary dialog.
         */
        @Parcelize
        object ImportGlossaryDialog : DialogConfig

        /**
         * Import TM dialog.
         */
        @Parcelize
        object ImportTmxDialog : DialogConfig

        /**
         * Export glossary dialog.
         */
        @Parcelize
        object ExportGlossaryDialog : DialogConfig
    }
}
