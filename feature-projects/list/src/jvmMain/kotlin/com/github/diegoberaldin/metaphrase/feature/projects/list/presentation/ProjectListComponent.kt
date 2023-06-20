package com.github.diegoberaldin.metaphrase.feature.projects.list.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ProjectListComponent {
    val uiState: StateFlow<ProjectListUiState>
    val projectSelected: SharedFlow<ProjectModel>
    val dialog: Value<ChildSlot<DialogConfiguration, *>>

    fun openRecent(value: RecentProjectModel)
    fun removeFromRecent(value: RecentProjectModel)
    fun closeDialog()

    sealed interface DialogConfiguration : Parcelable {
        @Parcelize
        object None : DialogConfiguration

        @Parcelize
        object OpenError : DialogConfiguration
    }
}
