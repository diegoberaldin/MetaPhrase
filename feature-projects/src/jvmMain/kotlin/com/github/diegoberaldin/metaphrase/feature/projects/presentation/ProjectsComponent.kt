package com.github.diegoberaldin.metaphrase.feature.projects.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType

/**
 * Projects component contract.
 */
interface ProjectsComponent :
    MviModel<ProjectsComponent.Intent, ProjectsComponent.UiState, ProjectsComponent.Effect> {

    sealed interface Intent {
        /**
         * Open a project with a given ID.
         *
         * @param projectId Project ID
         */
        data class Open(val projectId: Int) : Intent

        /**
         * Close the current project.
         */
        object CloseCurrentProject : Intent

        /**
         * Save the current project.
         *
         * @param path path of the TMX file
         */
        data class SaveCurrentProject(val path: String) : Intent

        /**
         * Import messages for the current language from a resource file.
         *
         * @param path file source path
         * @param type resource type
         */
        data class Import(val path: String, val type: ResourceFileType) : Intent

        /**
         * Export the messages of the current language to a resource file.
         *
         * @param path file destination path
         * @param type resource type
         */
        data class Export(val path: String, val type: ResourceFileType) : Intent

        /**
         * Move the cursor to the previous message.
         */
        object MoveToPrevious : Intent

        /**
         * Move the cursor to the next message.
         */
        object MoveToNext : Intent

        /**
         * Close the current editing operation.
         */
        object EndEditing : Intent

        /**
         * Copy the base (source) message to the target editor field.
         */
        object CopyBase : Intent

        /**
         * Add a new segment (trigger dialog)
         */
        object AddSegment : Intent

        /**
         * Delete the current segment.
         */
        object DeleteSegment : Intent

        /**
         * Export the TM content to a  TMX file.
         *
         * @param path Path
         */
        data class ExportTmx(val path: String) : Intent

        /**
         * Start a placeholder validation.
         */
        object ValidatePlaceholders : Intent

        /**
         * Insert the best TM match into the translation editor.
         */
        object InsertBestMatch : Intent

        /**
         * Starts a global spellcheck validation.
         */
        object GlobalSpellcheck : Intent

        /**
         * Saves all the messages of the current project in the global TM.
         */
        object SyncWithTm : Intent

        /**
         * Retrieve a suggestion from the MT provider.
         */
        object MachineTranslationRetrieve : Intent

        /**
         * Insert the MT suggestion in the translation editor field.
         */
        object MachineTranslationInsert : Intent

        /**
         * Copy the target message in the editor to the TM suggestion.
         */
        object MachineTranslationCopyTarget : Intent

        /**
         * Share the current suggestion to the TM provider.
         */
        object MachineTranslationShare : Intent

        /**
         * Share the whole content of the project with the TM provider.
         */
        object MachineTranslationContributeTm : Intent
    }

    /**
     * UI state.
     *
     * @property activeProject Currently active (opened) project.
     * @property isEditing True if a message (Translation Unit) is being edited.
     * @property currentLanguage Currently selected language in the translation toolbar (if any).
     * @constructor Create [UiState]
     */
    data class UiState(
        val activeProject: ProjectModel? = null,
        val isEditing: Boolean = false,
        val currentLanguage: LanguageModel? = null,
    )

    sealed interface Effect

    /**
     * Navigation stack for the project list/detail.
     */
    val childStack: Value<ChildStack<Config, *>>

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
