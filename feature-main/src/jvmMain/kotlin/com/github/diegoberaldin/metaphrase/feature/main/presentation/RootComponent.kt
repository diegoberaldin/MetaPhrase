package com.github.diegoberaldin.metaphrase.feature.main.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val main: Value<ChildSlot<Config, *>>
    val dialog: Value<ChildSlot<DialogConfig, *>>
    val uiState: StateFlow<RootUiState>

    fun openDialog()
    fun openProject(path: String)
    fun openEditProject()
    fun saveCurrentProject()
    fun saveProjectAs()
    fun saveProject(path: String)
    fun openNewDialog()
    fun closeDialog()
    fun hasUnsavedChanges(): Boolean
    fun closeCurrentProject(closeAfter: Boolean = false)
    fun confirmCloseCurrentProject(openAfter: Boolean = false, newAfter: Boolean = false)
    fun openStatistics()
    fun openSettings()
    fun openImportDialog(type: ResourceFileType)
    fun openExportDialog(type: ResourceFileType)
    fun import(path: String, type: ResourceFileType)
    fun export(path: String, type: ResourceFileType)
    fun moveToPreviousSegment()
    fun moveToNextSegment()
    fun endEditing()
    fun copyBase()
    fun addSegment()
    fun deleteSegment()
    fun openExportTmxDialog()
    fun exportTmx(path: String)
    fun openImportTmxDialog()
    fun importTmx(path: String)
    fun clearTm()
    fun syncTm()
    fun validatePlaceholders()
    fun insertBestMatch()
    fun globalSpellcheck()
    fun openImportGlossaryDialog()
    fun importGlossary(path: String)
    fun openExportGlossaryDialog()
    fun exportGlossary(path: String)
    fun clearGlossary()

    sealed interface Config : Parcelable {
        @Parcelize
        object Intro : Config

        @Parcelize
        object Projects : Config
    }

    sealed interface DialogConfig : Parcelable {

        @Parcelize
        object None : DialogConfig

        @Parcelize
        object OpenDialog : DialogConfig

        @Parcelize
        object NewDialog : DialogConfig

        @Parcelize
        object EditDialog : DialogConfig

        @Parcelize
        data class SaveAsDialog(val name: String) : DialogConfig

        @Parcelize
        data class ConfirmCloseDialog(
            val closeAfter: Boolean = false,
            val openAfter: Boolean = false,
            val newAfter: Boolean = false,
        ) : DialogConfig

        @Parcelize
        data class ImportDialog(val type: ResourceFileType) : DialogConfig

        @Parcelize
        data class ExportDialog(val type: ResourceFileType) : DialogConfig

        @Parcelize
        object StatisticsDialog : DialogConfig

        @Parcelize
        object SettingsDialog : DialogConfig

        @Parcelize
        object ExportTmxDialog : DialogConfig

        @Parcelize
        object ImportGlossaryDialog : DialogConfig

        @Parcelize
        object ImportTmxDialog : DialogConfig

        @Parcelize
        object ExportGlossaryDialog : DialogConfig
    }
}
