package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.LoginComponent
import com.github.diegoberaldin.feature.main.settings.dialog.login.ui.LoginDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.presentation.SettingsComponent
import java.awt.Cursor

/**
 * UI content of the settings dialog.
 *
 * @param component component
 * @param onClose on close callback
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsDialog(
    component: SettingsComponent,
    onClose: () -> Unit,
) {
    val lang by L10n.currentLanguage.collectAsState("lang".localized())
    LaunchedEffect(lang) {}

    MetaPhraseTheme {
        Window(
            title = "dialog_title_settings".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose()
            },
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
                modifier = Modifier.size(600.dp, 400.dp)
                    .pointerHoverIcon(pointerIcon)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        start = Spacing.s,
                        end = Spacing.s,
                        top = Spacing.xs,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f).padding(
                            top = Spacing.m,
                            bottom = Spacing.m,
                            start = Spacing.s,
                            end = Spacing.m,
                        ),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_language".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        val availableLanguages = uiState.availableLanguages
                        CustomSpinner(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp),
                            ),
                            size = DpSize(width = 200.dp, height = 30.dp),
                            values = availableLanguages.map { it.name },
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            current = uiState.currentLanguage?.name,
                            onValueChanged = {
                                val language = availableLanguages[it]
                                component.reduce(SettingsComponent.Intent.SetLanguage(language))
                            },
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_similarity_threshold".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CustomTextField(
                            modifier = Modifier.width(80.dp).height(26.dp),
                            value = uiState.similarityThreshold,
                            onValueChange = {
                                component.reduce(SettingsComponent.Intent.SetSimilarity(it))
                            },
                        )
                        Text(
                            modifier = Modifier.padding(Spacing.s),
                            text = "%",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_spellcheck_enabled".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Checkbox(
                            checked = uiState.spellcheckEnabled,
                            onCheckedChange = {
                                component.reduce(SettingsComponent.Intent.SetSpellcheckEnabled(it))
                            },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_machine_translation_provider".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        val providers = uiState.availableProviders
                        CustomSpinner(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp),
                            ),
                            size = DpSize(width = 200.dp, height = 30.dp),
                            values = providers.map { it.readableName },
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            current = uiState.currentProvider?.readableName.orEmpty(),
                            onValueChanged = {
                                component.reduce(SettingsComponent.Intent.SetMachineTranslationProvider(it))
                            },
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_machine_translation_key".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        val keyGenerationEnabled =
                            uiState.currentProvider == MachineTranslationProvider.MY_MEMORY
                        if (keyGenerationEnabled) {
                            CustomTooltipArea(
                                text = "tooltip_login".localized(),
                            ) {
                                Button(
                                    modifier = Modifier.heightIn(max = 25.dp),
                                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = Spacing.s),
                                    onClick = {
                                        component.reduce(SettingsComponent.Intent.OpenLoginDialog)
                                    },
                                ) {
                                    Text(
                                        text = "button_generate".localized(),
                                        style = MaterialTheme.typography.labelLarge,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(Spacing.s))
                        }
                        CustomTextField(
                            modifier = Modifier.width(200.dp).height(26.dp),
                            value = uiState.key,
                            enabled = !keyGenerationEnabled,
                            onValueChange = {
                                component.reduce(SettingsComponent.Intent.SetMachineTranslationKey(it))
                            },
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_dark_mode".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Checkbox(
                            checked = uiState.darkModeEnabled,
                            onCheckedChange = {
                                component.reduce(SettingsComponent.Intent.SetDarkMode(it))
                            },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_editor_font_size".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box {
                            CustomTextField(
                                modifier = Modifier.width(80.dp).height(26.dp),
                                value = uiState.editorFontSize,
                                enabled = false,
                                onValueChange = {},
                            )
                            Column(
                                modifier = Modifier.matchParentSize().padding(top = Spacing.xxs),
                                horizontalAlignment = Alignment.End,
                            ) {
                                Icon(
                                    modifier = Modifier.weight(1f).padding(horizontal = Spacing.xxs).onClick {
                                        val newSize = uiState.editorFontSize.toInt() + 1
                                        component.reduce(SettingsComponent.Intent.SetEditorFontSize(newSize))
                                    },
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = null,
                                )
                                Icon(
                                    modifier = Modifier.weight(1f).padding(horizontal = Spacing.xxs).onClick {
                                        val newSize = (uiState.editorFontSize.toInt() - 1).coerceAtLeast(10)
                                        component.reduce(SettingsComponent.Intent.SetEditorFontSize(newSize))
                                    },
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                )
                            }
                        }
                        Text(
                            modifier = Modifier.padding(Spacing.s),
                            text = "pt",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_version".localized(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = uiState.appVersion,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }

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
                        Text(text = "button_close".localized(), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }

            val dialog by component.dialog.subscribeAsState()
            when (dialog.child?.configuration) {
                SettingsComponent.DialogConfig.Login -> {
                    val child = dialog.child?.instance as LoginComponent
                    LoginDialog(
                        component = child,
                        onClose = { u, p ->
                            if (u != null && p != null) {
                                component.reduce(
                                    SettingsComponent.Intent.GenerateMachineTranslationKey(
                                        username = u,
                                        password = p,
                                    ),
                                )
                            }
                            component.reduce(SettingsComponent.Intent.CloseDialog)
                        },
                    )
                }

                else -> Unit
            }
        }
    }
}
