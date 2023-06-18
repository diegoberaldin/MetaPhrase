package com.github.diegoberaldin.metaphrase.feature.main.ui

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

        is RootComponent.DialogConfig.SaveAsDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "${config.name}.tmx",
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.saveProject(path = path)
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
                        else -> false
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
                    ResourceFileType.RESX -> "Resources.resx"
                    ResourceFileType.PO -> "messages.po"
                    ResourceFileType.JSON -> "strings.json"
                    ResourceFileType.ARB -> "strings.arb"
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

        is RootComponent.DialogConfig.ImportGlossaryDialog -> {
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = { it.endsWith(".csv") },
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.importGlossary(path = path)
                    }
                },
            )
        }

        is RootComponent.DialogConfig.ExportGlossaryDialog -> {
            CustomSaveFileDialog(
                title = "dialog_title_open_file".localized(),
                initialFileName = "glossary.csv",
                onCloseRequest = { path ->
                    component.closeDialog()
                    if (path != null) {
                        component.exportGlossary(path = path)
                    }
                },
            )
        }

        else -> Unit
    }
}
