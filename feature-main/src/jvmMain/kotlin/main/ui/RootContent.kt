package main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.components.CustomOpenFileDialog
import common.ui.components.CustomSaveFileDialog
import common.ui.theme.Spacing
import data.ResourceFileType
import dialognewproject.presentation.CreateProjectComponent
import dialognewproject.ui.CreateProjectDialog
import dialogsettings.ui.SettingsComponent
import dialogsettings.ui.SettingsDialog
import dialogstatistics.presentation.StatisticsComponent
import dialogstatistics.ui.StatisticsDialog
import intro.presentation.IntroComponent
import intro.ui.MainEmptyContent
import localized
import main.presentation.RootComponent
import projects.presentation.ProjectsComponent
import projects.ui.ProjectsContent
import java.awt.Cursor

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val mainSlot by component.main.subscribeAsState()
    val uiState by component.uiState.collectAsState()

    val pointerIcon by remember(uiState.isLoading) {
        if (uiState.isLoading) {
            mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)))
        } else {
            mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)))
        }
    }

    Box(
        modifier = modifier
            .pointerHoverIcon(pointerIcon)
            .padding(horizontal = Spacing.s, vertical = Spacing.xs),
    ) {
        val child = mainSlot.child
        when (child?.configuration) {
            RootComponent.Config.Projects -> {
                ProjectsContent(
                    component = child.instance as ProjectsComponent,
                )
            }

            RootComponent.Config.Intro -> {
                MainEmptyContent(
                    component = child.instance as IntroComponent,
                )
            }

            else -> Unit
        }
    }

    // dialogs
    val dialogState by component.dialog.subscribeAsState()
    when (val config = dialogState.child?.configuration ?: RootComponent.DialogConfig.None) {
        RootComponent.DialogConfig.NewDialog -> {
            CreateProjectDialog(
                title = "dialog_title_create_project".localized(),
                component = dialogState.child?.instance as CreateProjectComponent,
                onClose = {
                    component.closeDialog()
                },
            )
        }

        is RootComponent.DialogConfig.EditDialog -> {
            CreateProjectDialog(
                title = "dialog_title_edit_project".localized(),
                component = dialogState.child?.instance as CreateProjectComponent,
                onClose = {
                    component.closeDialog()
                },
            )
        }

        is RootComponent.DialogConfig.ImportDialog -> {
            val type = config.type
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = {
                    when (type) {
                        ResourceFileType.ANDROID_XML -> {
                            it.endsWith(".xml")
                        }

                        ResourceFileType.IOS_STRINGS -> {
                            it.endsWith(".strings")
                        }

                        ResourceFileType.WINDOWS_RESX -> {
                            it.endsWith(".resx")
                        }

                        else -> {
                            false
                        }
                    }
                },
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.import(path = path, type = type)
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ExportDialog -> {
            val type = config.type
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = when (type) {
                    ResourceFileType.ANDROID_XML -> "strings.xml"
                    ResourceFileType.IOS_STRINGS -> "Localizable.strings"
                    ResourceFileType.WINDOWS_RESX -> "strings.resx"
                    else -> "strings"
                },
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.export(path = path, type = type)
                    }
                },
            )
        }

        is RootComponent.DialogConfig.StatisticsDialog -> {
            StatisticsDialog(
                component = dialogState.child?.instance as StatisticsComponent,
                onClose = {
                    component.closeDialog()
                },
            )
        }

        is RootComponent.DialogConfig.SettingsDialog -> {
            SettingsDialog(
                component = dialogState.child?.instance as SettingsComponent,
                onClose = {
                    component.closeDialog()
                },
            )
        }

        is RootComponent.DialogConfig.ExportTmxDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "memory.tmx",
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.exportTmx(path = path)
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ImportTmxDialog -> {
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = { it.endsWith(".tmx") },
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.importTmx(path = path)
                    }
                },
            )
        }

        else -> Unit
    }
}
