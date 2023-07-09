package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.onClick
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation.CreateProjectComponent
import java.awt.Cursor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateProjectDialog(
    title: String,
    component: CreateProjectComponent,
    onClose: () -> Unit,
) {
    MetaPhraseTheme {
        Window(
            title = title,
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose()
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
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerHoverIcon(pointerIcon)
                        .padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_project_field_name".localized(),
                        value = uiState.name,
                        onValueChange = {
                            component.reduce(CreateProjectComponent.Intent.SetName(it))
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.nameError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Row {
                        Text(
                            text = "create_project_languages".localized(),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        var languageDropdownExpanded by remember {
                            mutableStateOf(false)
                        }
                        Box {
                            CustomTooltipArea(
                                text = "tooltip_add".localized(),
                            ) {
                                Icon(
                                    modifier = Modifier.size(24.dp)
                                        .onClick {
                                            languageDropdownExpanded = true
                                        },
                                    imageVector = Icons.Default.AddCircle,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = null,
                                )
                            }
                            SelectLanguageDropDown(
                                expanded = languageDropdownExpanded,
                                languages = uiState.availableLanguages,
                                onDismiss = {
                                    languageDropdownExpanded = false
                                },
                                onSelected = {
                                    component.reduce(CreateProjectComponent.Intent.AddLanguage(it))
                                    languageDropdownExpanded = false
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Text(
                        text = "create_project_languages_subtitle".localized(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelSmall,
                    )
                    Spacer(modifier = Modifier.height(Spacing.s))
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        items(uiState.languages) { language ->
                            LanguageCell(
                                language = language,
                                onSelected = {
                                    component.reduce(CreateProjectComponent.Intent.SetBaseLanguage(language))
                                },
                                onDeleteClicked = {
                                    component.reduce(CreateProjectComponent.Intent.RemoveLanguage(language))
                                },
                            )
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
                                onClose()
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.labelLarge)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.reduce(CreateProjectComponent.Intent.Submit)
                            },
                        ) {
                            Text(text = "button_ok".localized(), style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}
