package main.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import common.utils.getByInjection
import data.ResourceFileType
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

interface RootComponent {

    val main: Value<ChildSlot<Config, *>>
    val dialog: Value<ChildSlot<DialogConfig, *>>

    val uiState: StateFlow<RootUiState>

    fun openEditProject()
    fun openNewDialog()
    fun closeDialog()
    fun closeCurrentProject()
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

    object Factory {
        fun create(componentContext: ComponentContext, coroutineContext: CoroutineContext): RootComponent =
            DefaultRootComponent(
                componentContext = componentContext,
                coroutineContext = coroutineContext,
                projectRepository = getByInjection(),
                dispatchers = getByInjection(),
                notificationCenter = getByInjection(),
            )
    }

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
        object NewDialog : DialogConfig

        @Parcelize
        object EditDialog : DialogConfig

        @Parcelize
        data class ImportDialog(val type: ResourceFileType) : DialogConfig

        @Parcelize
        data class ExportDialog(val type: ResourceFileType) : DialogConfig

        @Parcelize
        object StatisticsDialog : DialogConfig

        @Parcelize
        object SettingsDialog : DialogConfig
    }
}
