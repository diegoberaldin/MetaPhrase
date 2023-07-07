package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.core.common.architecture.MviModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel

/**
 * Recent project list component contract.
 */
interface ProjectListComponent :
    MviModel<ProjectListComponent.Intent, ProjectListComponent.UiState, ProjectListComponent.Effect> {
    /**
     * View intents.
     */
    sealed interface Intent {
        /**
         * Open a recent project.
         *
         * @property value project to open
         * @constructor Create [OpenRecent]
         */
        data class OpenRecent(val value: RecentProjectModel) : Intent

        /**
         * Remove an item from the recent project list .
         *
         * @property value project to remove
         * @constructor Create [RemoveFromRecent]
         */
        data class RemoveFromRecent(val value: RecentProjectModel) : Intent

        /**
         * Close the currently opened dialog.
         */
        object CloseDialog : Intent
    }

    /**
     * Project list UI state.
     *
     * @property projects list of recent projects
     * @constructor Create [UiState]
     */
    data class UiState(val projects: List<RecentProjectModel> = emptyList())

    /**
     * Effects.
     */
    sealed interface Effect {
        /**
         * Event emitted when a recent project is selected.
         *
         * @property value selected project
         * @constructor Create [ProjectSelected]
         */
        data class ProjectSelected(val value: ProjectModel) : Effect
    }

    val dialog: Value<ChildSlot<DialogConfiguration, *>>

    /**
     * Available dialog configurations.
     */
    sealed interface DialogConfiguration : Parcelable {
        /**
         * None
         */
        @Parcelize
        object None : DialogConfiguration

        /**
         * Error dialog
         */
        @Parcelize
        object OpenError : DialogConfiguration
    }
}
