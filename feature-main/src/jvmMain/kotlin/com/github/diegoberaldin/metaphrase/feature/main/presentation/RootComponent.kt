package com.github.diegoberaldin.metaphrase.feature.main.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow

/**
 * Root component.
 */
interface RootComponent {

    /**
     * Navigation slot for the main content.
     */
    val main: Value<ChildSlot<Config, *>>

    /**
     * Navigation slot for currently opened dialog.
     */
    val dialog: Value<ChildSlot<DialogConfig, *>>

    /**
     * UI state.
     */
    val uiState: StateFlow<RootUiState>

    /**
     * Open the "Open pooject" dialog.
     */
    fun openDialog()

    /**
     * Loads a translation project from disk.
     *
     * @param path path of the TMX file
     */
    fun openProject(path: String)

    /**
     * Open the "Edit project" dialog.
     */
    fun openEditProject()

    /**
     * Save the current project in the TMX at the specified path.
     */
    fun saveCurrentProject()

    /**
     * Opens the "Save as" project dialog.
     */
    fun saveProjectAs()

    /**
     * Save the project as a TMX file at the given path.
     *
     * @param path path of the TMX to write
     */
    fun saveProject(path: String)

    /**
     * Open the "New project" dialog.
     */
    fun openNewDialog()

    /**
     * Close any opened dialog.
     */
    fun closeDialog()

    /**
     * Returns whether the current project has any unsaved changes.
     *
     * @return true if there are unsaved changed
     */
    fun hasUnsavedChanges(): Boolean

    /**
     * Displays the "Confirm project close" dialog.
     *
     * @param closeAfter if set to true, after confirmation the application will exit
     */
    fun closeCurrentProject(closeAfter: Boolean = false)

    /**
     * Sends user confirmation to close the current project with an optional action afterwards.
     *
     * @param openAfter if set to true, after closing the "Open project" dialog will be opened
     * @param newAfter if set to true, after closing the "New project" dialog will be opened
     */
    fun confirmCloseCurrentProject(openAfter: Boolean = false, newAfter: Boolean = false)

    /**
     * Open the "Statistics" dialog.
     */
    fun openStatistics()

    /**
     * Open the "Settings" dialog.
     */
    fun openSettings()

    /**
     * Open the import dialog for the current language.
     *
     * @param type resource file type
     */
    fun openImportDialog(type: ResourceFileType)

    /**
     * Open the export dialog for the current language.
     *
     * @param type resource file type
     */
    fun openExportDialog(type: ResourceFileType)

    /**
     * Import messages from a resource file.
     *
     * @param path file path
     * @param type resource type
     */
    fun import(path: String, type: ResourceFileType)

    /**
     * Export messages to a resource file.
     *
     * @param path file path
     * @param type resource type
     */
    fun export(path: String, type: ResourceFileType)

    /**
     * Navigate to the previous segment in the editor.
     */
    fun moveToPreviousSegment()

    /**
     * Navigate to the next segment in the editor.
     */
    fun moveToNextSegment()

    /**
     * End editing the current message.
     */
    fun endEditing()

    /**
     * Copy the base (source) message to the target message.
     */
    fun copyBase()

    /**
     * Open the "New segment" dialog.
     */
    fun addSegment()

    /**
     * Delete the current segment.
     */
    fun deleteSegment()

    /**
     * Open the dialog to export the current TM content to TMX.
     */
    fun openExportTmxDialog()

    /**
     * Export the current TM content to a TMX file.
     *
     * @param path TMX file path
     */
    fun exportTmx(path: String)

    /**
     * Open the import dialog to populate the TM.
     */
    fun openImportTmxDialog()

    /**
     * Import a TMX file to the translation memory.
     *
     * @param path file path
     */
    fun importTmx(path: String)

    /**
     * Clear the content of the global Translation Memory.
     */
    fun clearTm()

    /**
     * Import all the messages of the current project into the global TM.
     */
    fun syncTm()

    /**
     * Starts the placeholder validation.
     */
    fun validatePlaceholders()

    /**
     * Insert the best TM match to the translation editor.
     */
    fun insertBestMatch()

    /**
     * Start a global spellcheck validation.
     */
    fun globalSpellcheck()

    /**
     * Open the import glossary dialog.
     */
    fun openImportGlossaryDialog()

    /**
     * Import a CSV file into the glossary.
     *
     * @param path file path
     */
    fun importGlossary(path: String)

    /**
     * Open the export glossary dialog.
     */
    fun openExportGlossaryDialog()

    /**
     * Export the global glossary to a CSV file.
     *
     * @param path file path
     */
    fun exportGlossary(path: String)

    /**
     * Clear the global glossary.
     */
    fun clearGlossary()

    /**
     * Retrieve a suggestion for the current message from the machine translation provider.
     */
    fun machineTranslationRetrieve()

    /**
     * Insert the current suggestion from machine translation in the editor.
     */
    fun machineTranslationInsert()

    /**
     * Copy the target message into the machine translation suggestion.
     */
    fun machineTranslationCopyTarget()

    /**
     * Share the current suggestion to the remote machine translation provider.
     */
    fun machineTranslationShare()

    /**
     * Share the whole project as a TM to the machine translation provider.
     */
    fun machineTranslationContributeTm()

    /**
     * Open the user manual.
     */
    fun openManual()

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
