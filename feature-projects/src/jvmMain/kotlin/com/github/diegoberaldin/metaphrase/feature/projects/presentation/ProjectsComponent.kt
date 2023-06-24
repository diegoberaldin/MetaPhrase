package com.github.diegoberaldin.metaphrase.feature.projects.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow

/**
 * Projects component.
 */
interface ProjectsComponent {
    /**
     * Currently active (opened) project.
     */
    val activeProject: StateFlow<ProjectModel?>

    /**
     * Navigation stack for the project list/detail.
     */
    val childStack: Value<ChildStack<Config, *>>

    /**
     * True if a message (Translation Unit) is being edited.
     */
    val isEditing: StateFlow<Boolean>

    /**
     * Currently selected language in the translate toolbar (if any).
     */
    val currentLanguage: StateFlow<LanguageModel?>

    /**
     * Open a project with a given ID.
     *
     * @param projectId Project ID
     */
    fun open(projectId: Int)

    /**
     * Close the current project.
     */
    fun closeCurrentProject()

    /**
     * Save the current project.
     *
     * @param path path of the TMX file
     */
    fun saveCurrentProject(path: String)

    /**
     * Import messages for the current language from a resource file.
     *
     * @param path file source path
     * @param type resource type
     */
    fun import(path: String, type: ResourceFileType)

    /**
     * Export the messages of the current language to a resource file.
     *
     * @param path file destination path
     * @param type resource type
     */
    fun export(path: String, type: ResourceFileType)

    /**
     * Move the cursor to the previous message.
     */
    fun moveToPrevious()

    /**
     * Move the cursor to the next message.
     */
    fun moveToNext()

    /**
     * Close the current editing operation.
     */
    fun endEditing()

    /**
     * Copy the base (source) message to the target editor field.
     */
    fun copyBase()

    /**
     * Add a new segment (trigger dialog)
     */
    fun addSegment()

    /**
     * Delete the current segment.
     */
    fun deleteSegment()

    /**
     * Export the TM content to a  TMX file.
     *
     * @param path Path
     */
    fun exportTmx(path: String)

    /**
     * Start a placeholder validation.
     */
    fun validatePlaceholders()

    /**
     * Insert the best TM match into the translation editor.
     */
    fun insertBestMatch()

    /**
     * Starts a global spellcheck validation.
     */
    fun globalSpellcheck()

    /**
     * Saves all the messages of the current project in the global TM.
     */
    fun syncWithTm()

    /**
     * Retrieve a suggestion from the MT provider.
     */
    fun machineTranslationRetrieve()

    /**
     * Insert the MT suggestion in the translation editor field.
     */
    fun machineTranslationInsert()

    /**
     * Copy the target message in the editor to the TM suggestion.
     */
    fun machineTranslationCopyTarget()

    /**
     * Share the current suggestion to the TM provider.
     */
    fun machineTranslationShare()

    /**
     * Share the whole content of the project with the TM provider.
     */
    fun machineTranslationContributeTm()

    /**
     * Available screen configuration.
     */
    sealed interface Config : Parcelable {
        /**
         * Project list
         */
        @Parcelize
        object List : Config

        /**
         * Project detail.
         *
         * @property projectId ID of the project
         * @constructor Create [Detail]
         */
        @Parcelize
        data class Detail(val projectId: Int) : Config
    }
}
