package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.appearance.presentation.AppearanceSettingsComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppearanceSettingsContent(
    modifier: Modifier = Modifier,
    component: AppearanceSettingsComponent,
) {
    val uiState by component.uiState.collectAsState()

    Column(
        modifier = modifier.padding(
            top = Spacing.m,
            bottom = Spacing.m,
            start = Spacing.s,
            end = Spacing.m,
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
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
                    component.reduce(AppearanceSettingsComponent.Intent.SetDarkMode(it))
                },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "dialog_settings_editor_font_type".localized(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.weight(1f))
            val fontTypes = uiState.availableFontTypes
            CustomSpinner(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp),
                ),
                size = DpSize(width = 200.dp, height = 30.dp),
                values = fontTypes,
                valueColor = MaterialTheme.colorScheme.onBackground,
                current = uiState.editorFontType,
                onValueChanged = {
                    component.reduce(AppearanceSettingsComponent.Intent.SetEditorFontType(it))
                },
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
                            component.reduce(AppearanceSettingsComponent.Intent.SetEditorFontSize(newSize))
                        },
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                    )
                    Icon(
                        modifier = Modifier.weight(1f).padding(horizontal = Spacing.xxs).onClick {
                            val newSize = (uiState.editorFontSize.toInt() - 1).coerceAtLeast(10)
                            component.reduce(AppearanceSettingsComponent.Intent.SetEditorFontSize(newSize))
                        },
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(Spacing.s),
                text = "unit_pt".localized(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}
