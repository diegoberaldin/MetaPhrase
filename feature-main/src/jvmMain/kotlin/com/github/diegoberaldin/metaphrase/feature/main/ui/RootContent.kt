package com.github.diegoberaldin.metaphrase.feature.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomOpenFileDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSaveFileDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.intro.presentation.IntroComponent
import com.github.diegoberaldin.metaphrase.feature.intro.ui.MainEmptyContent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation.SettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.ui.SettingsDialog
import com.github.diegoberaldin.metaphrase.feature.main.presentation.RootComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation.CreateProjectComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.ui.CreateProjectDialog
import com.github.diegoberaldin.metaphrase.feature.projects.presentation.ProjectsComponent
import com.github.diegoberaldin.metaphrase.feature.projects.ui.ProjectsContent
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.StatisticsComponent
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.ui.StatisticsDialog
import java.awt.Cursor

/**
 * Root UI content of the application. This contains the logic that routes either towards the intro (welcome) screen or
 * towards the list of projects.
 *
 * @param component component
 * @param modifier compose modifier
 * @param onExitApplication application exit callback
 */
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
    onExitApplication: () -> Unit = {},
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
        modifier = modifier.padding(horizontal = Spacing.s, vertical = Spacing.xs),
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

        // Overlay
        if (uiState.isLoading) {
            Surface(color = Color.Transparent) {
                Box(modifier = Modifier.fillMaxSize().pointerHoverIcon(icon = pointerIcon))
            }
        }
    }

    // dialogs
    val dialogState by component.dialog.subscribeAsState()
    when (val config = dialogState.child?.configuration ?: RootComponent.DialogConfig.None) {
        RootComponent.DialogConfig.OpenDialog -> {
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = { it.endsWith(".tmx") },
                onCloseRequest = { path ->
                    if (path != null) {
                        component.reduce(RootComponent.Intent.OpenProject(path = path))
                    }
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        is RootComponent.DialogConfig.ConfirmCloseDialog -> {
            CustomDialog(
                title = "dialog_title_warning".localized(),
                message = "message_confirm_close".localized(),
                buttonTexts = listOf("button_cancel".localized(), "button_ok".localized()),
                onClose = { buttonIndex ->
                    if (buttonIndex == 1) {
                        if (config.closeAfter) {
                            onExitApplication()
                        } else {
                            component.reduce(
                                RootComponent.Intent.ConfirmCloseCurrentProject(
                                    newAfter = config.newAfter,
                                    openAfter = config.openAfter,
                                ),
                            )
                        }
                    }
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        RootComponent.DialogConfig.NewDialog -> {
            CreateProjectDialog(
                title = "dialog_title_create_project".localized(),
                component = dialogState.child?.instance as CreateProjectComponent,
                onClose = {
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        is RootComponent.DialogConfig.EditDialog -> {
            CreateProjectDialog(
                title = "dialog_title_edit_project".localized(),
                component = dialogState.child?.instance as CreateProjectComponent,
                onClose = {
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        is RootComponent.DialogConfig.SaveAsDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "${config.name}.tmx",
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.SaveProject(path = path))
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ImportDialog -> {
            val type = config.type
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = {
                    when (type) {
                        ResourceFileType.ANDROID_XML -> it.endsWith(".xml")
                        ResourceFileType.IOS_STRINGS -> it.endsWith(".strings")
                        ResourceFileType.RESX -> it.endsWith(".resx")
                        ResourceFileType.PO -> it.endsWith(".po")
                        ResourceFileType.JSON -> it.endsWith(".json")
                        ResourceFileType.ARB -> it.endsWith(".arb")
                        ResourceFileType.PROPERTIES -> it.endsWith(".properties")
                        else -> false
                    }
                },
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.Import(path = path, type = type))
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
                    ResourceFileType.RESX -> "Resources.resx"
                    ResourceFileType.PO -> "messages.po"
                    ResourceFileType.JSON -> "strings.json"
                    ResourceFileType.ARB -> "strings.arb"
                    ResourceFileType.PROPERTIES -> "dictionary.properties"
                    else -> "strings"
                },
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.Export(path = path, type = type))
                    }
                },
            )
        }

        is RootComponent.DialogConfig.StatisticsDialog -> {
            StatisticsDialog(
                component = dialogState.child?.instance as StatisticsComponent,
                onClose = {
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        is RootComponent.DialogConfig.SettingsDialog -> {
            SettingsDialog(
                component = dialogState.child?.instance as SettingsComponent,
                onClose = {
                    component.reduce(RootComponent.Intent.CloseDialog)
                },
            )
        }

        is RootComponent.DialogConfig.ExportTmxDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "memory.tmx",
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.ExportTmx(path = path))
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ImportTmxDialog -> {
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = { it.endsWith(".tmx") },
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.ImportTmx(path = path))
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ImportGlossaryDialog -> {
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = { it.endsWith(".csv") },
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.ImportGlossary(path = path))
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ExportGlossaryDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "glossary.csv",
                onCloseRequest = { path ->
                    component.reduce(RootComponent.Intent.CloseDialog)
                    if (path != null) {
                        component.reduce(RootComponent.Intent.ExportGlossary(path = path))
                    }
                },
            )
        }

        else -> Unit
    }
}
