package main.ui

import StatisticsComponent
import StatisticsDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import common.ui.components.CustomOpenFileDialog
import common.ui.components.CustomSaveFileDialog
import common.ui.theme.Spacing
import data.ResourceFileType
import intro.ui.IntroComponent
import intro.ui.MainEmptyContent
import localized
import mainsettings.ui.SettingsComponent
import mainsettings.ui.SettingsDialog
import projects.ui.ProjectsComponent
import projects.ui.ProjectsContent
import projectscreate.ui.CreateProjectComponent
import projectscreate.ui.CreateProjectDialog

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val mainSlot by component.main.subscribeAsState()
    val uiState by component.uiState.collectAsState()

    Box(modifier = modifier.padding(horizontal = Spacing.s, vertical = Spacing.xs)) {
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

        else -> Unit
    }
}
