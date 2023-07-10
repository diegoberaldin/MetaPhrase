package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTabBar
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.L10n
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation.AppearanceSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.ui.AppearanceSettingsContent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation.GeneralSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.ui.GeneralSettingsContent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation.MachineTranslationSettingsComponent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.ui.MachineTranslationSettingsContent
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.main.presentation.SettingsComponent
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
                Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    CustomTabBar(
                        tabs = listOf(
                            "settings_tab_general".localized(),
                            "settings_tab_appearance".localized(),
                            "settings_tab_general_machine_translation".localized(),
                        ),
                        current = uiState.currentTab,
                        onTabSelected = {
                            component.reduce(SettingsComponent.Intent.ChangeTab(index = it))
                        },
                    )
                    Box(
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                                shape = if (uiState.currentTab == 0) {
                                    RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 4.dp,
                                        bottomStart = 4.dp,
                                        bottomEnd = 4.dp,
                                    )
                                } else {
                                    RoundedCornerShape(4.dp)
                                },
                            ),
                    ) {
                        val config by component.config.subscribeAsState()
                        val contentModifier = Modifier.fillMaxWidth()
                        when (config.child?.configuration) {
                            SettingsComponent.ContentConfig.General -> GeneralSettingsContent(
                                modifier = contentModifier,
                                component = config.child?.instance as GeneralSettingsComponent,
                            )

                            SettingsComponent.ContentConfig.Appearance -> AppearanceSettingsContent(
                                modifier = contentModifier,
                                component = config.child?.instance as AppearanceSettingsComponent,
                            )

                            SettingsComponent.ContentConfig.MachineTranslation -> MachineTranslationSettingsContent(
                                modifier = contentModifier,
                                component = config.child?.instance as MachineTranslationSettingsComponent,
                            )

                            else -> Unit
                        }
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
        }
    }
}
