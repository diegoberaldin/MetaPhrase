package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.general.presentation.GeneralSettingsComponent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GeneralSettingsContent(
    modifier: Modifier = Modifier,
    component: GeneralSettingsComponent,
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
                    component.reduce(GeneralSettingsComponent.Intent.SetLanguage(language))
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
                    component.reduce(GeneralSettingsComponent.Intent.SetSimilarity(it))
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
                    component.reduce(GeneralSettingsComponent.Intent.SetSpellcheckEnabled(it))
                },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary),
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
}
