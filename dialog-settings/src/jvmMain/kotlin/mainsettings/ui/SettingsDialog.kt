package mainsettings.ui

import L10n
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import common.ui.components.CustomSpinner
import common.ui.components.CustomTextField
import common.ui.theme.MetaPhraseTheme
import common.ui.theme.SelectedBackground
import common.ui.theme.Spacing
import localized

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

            Column(
                modifier = Modifier.size(600.dp, 400.dp)
                    .background(MaterialTheme.colors.background)
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
                    verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_language".localized(),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        val availableLanguages = uiState.availableLanguages
                        CustomSpinner(
                            modifier = Modifier.background(
                                color = SelectedBackground,
                                shape = RoundedCornerShape(4.dp),
                            ),
                            size = DpSize(width = 200.dp, height = 30.dp),
                            values = availableLanguages.map { it.name },
                            valueColor = MaterialTheme.colors.onBackground,
                            current = uiState.currentLanguage?.name,
                            onValueChanged = {
                                val language = availableLanguages[it]
                                component.setLanguage(language)
                            },
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_similarity_threshold".localized(),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CustomTextField(
                            modifier = Modifier.width(80.dp).height(26.dp),
                            value = uiState.similarityThreshold.toString(),
                            onValueChange = {
                                component.setSimilarity(it)
                            },
                        )
                        Text(
                            modifier = Modifier.padding(Spacing.s),
                            text = "%",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "dialog_settings_spellcheck_enabled".localized(),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Checkbox(
                            checked = uiState.spellcheckEnabled,
                            onCheckedChange = {
                                component.setSpellcheckEnabled(it)
                            },
                            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary),
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "dialog_settings_version".localized(),
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = uiState.appVersion,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onBackground,
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
                        Text(text = "button_close".localized(), style = MaterialTheme.typography.button)
                    }
                }
            }
        }
    }
}
