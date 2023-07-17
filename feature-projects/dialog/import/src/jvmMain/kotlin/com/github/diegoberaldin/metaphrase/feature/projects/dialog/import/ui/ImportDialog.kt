package com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomOpenFileDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation.ImportDialogComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.awt.Cursor

/**
 * Export dialog UI content.
 *
 * @param component Component
 * @param onClose On close callback
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImportDialog(
    component: ImportDialogComponent,
    onClose: (refresh: Boolean) -> Unit,
) {
    LaunchedEffect(component) {
        component.effects.onEach {
            when (it) {
                ImportDialogComponent.Effect.Done -> onClose(true)
            }
        }.launchIn(this)
    }
    MetaPhraseTheme {
        Window(
            title = "dialog_title_import".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose(false)
            },
        ) {
            Surface(
                modifier = Modifier.size(600.dp, 400.dp).background(MaterialTheme.colorScheme.background),
            ) {
                val uiState by component.uiState.collectAsState()
                val pointerIcon by remember(uiState.isLoading) {
                    if (uiState.isLoading) {
                        mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)))
                    } else {
                        mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)))
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize().pointerHoverIcon(pointerIcon)
                        .padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "export_select_resource_type".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        val availableTypes = uiState.availableResourceTypes
                        CustomSpinner(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp),
                            ),
                            size = DpSize(width = 200.dp, height = 30.dp),
                            values = availableTypes.map { it.toReadableName() },
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            current = uiState.selectedResourceType?.toReadableName(),
                            onValueChanged = {
                                val type = availableTypes[it]
                                component.reduce(ImportDialogComponent.Intent.SelectType(type))
                            },
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.resourceTypeError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))

                    Text(
                        text = "create_project_languages".localized(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))

                    val languages = uiState.languages.keys.toList()
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        items(languages) { language ->
                            val path = uiState.languages[language].orEmpty()
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(Spacing.xxs),
                            ) {
                                Text(
                                    text = language.name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                                Spacer(modifier = Modifier.width(Spacing.xxxs))
                                CustomTextField(
                                    modifier = Modifier.fillMaxWidth().height(26.dp).onClick {
                                        component.reduce(ImportDialogComponent.Intent.OpenFileDialog(language.code))
                                    },
                                    enabled = false,
                                    hint = "placeholder_select_file".localized(),
                                    singleLine = true,
                                    value = path,
                                    onValueChange = {},
                                    endButton = {
                                        Icon(
                                            imageVector = Icons.Default.FolderZip,
                                            contentDescription = null,
                                        )
                                    },
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.languagesError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )

                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose(false)
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.labelLarge)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.reduce(ImportDialogComponent.Intent.Submit)
                            },
                        ) {
                            Text(text = "button_ok".localized(), style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }

    val dialog by component.dialog.subscribeAsState()
    when (val config = dialog.child?.configuration) {
        is ImportDialogComponent.DialogConfig.OpenFile -> {
            val uiState by component.uiState.collectAsState()
            CustomOpenFileDialog(
                title = "dialog_title_open_file".localized(),
                nameFilter = {
                    when (uiState.selectedResourceType) {
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
                    component.reduce(
                        ImportDialogComponent.Intent.SetInputPath(
                            lang = config.lang,
                            path = path.orEmpty(),
                        ),
                    )
                    component.reduce(ImportDialogComponent.Intent.CloseDialog)
                },
            )
        }

        else -> Unit
    }
}

private fun ResourceFileType.toReadableName(): String = when (this) {
    ResourceFileType.ANDROID_XML -> "menu_project_import_android".localized()
    ResourceFileType.IOS_STRINGS -> "menu_project_import_ios".localized()
    ResourceFileType.RESX -> "menu_project_import_resx".localized()
    ResourceFileType.PO -> "menu_project_import_po".localized()
    ResourceFileType.JSON -> "menu_project_import_json".localized()
    ResourceFileType.ARB -> "menu_project_import_arb".localized()
    ResourceFileType.PROPERTIES -> "menu_project_import_properties".localized()
    else -> ""
}
