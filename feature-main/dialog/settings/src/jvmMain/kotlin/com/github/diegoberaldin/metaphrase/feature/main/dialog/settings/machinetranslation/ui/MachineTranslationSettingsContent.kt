package com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.github.diegoberaldin.feature.main.settings.dialog.login.presentation.LoginComponent
import com.github.diegoberaldin.feature.main.settings.dialog.login.ui.LoginDialog
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomSpinner
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.machinetranslation.presentation.MachineTranslationSettingsComponent

@Composable
fun MachineTranslationSettingsContent(
    modifier: Modifier = Modifier,
    component: MachineTranslationSettingsComponent,
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
                    component.reduce(MachineTranslationSettingsComponent.Intent.SetMachineTranslationProvider(it))
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
                            component.reduce(MachineTranslationSettingsComponent.Intent.OpenLoginDialog)
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
                    component.reduce(MachineTranslationSettingsComponent.Intent.SetMachineTranslationKey(it))
                },
            )
        }
    }

    val dialog by component.dialog.subscribeAsState()
    when (dialog.child?.configuration) {
        MachineTranslationSettingsComponent.DialogConfig.Login -> {
            val child = dialog.child?.instance as LoginComponent
            LoginDialog(
                component = child,
                onClose = { u, p ->
                    if (u != null && p != null) {
                        component.reduce(
                            MachineTranslationSettingsComponent.Intent.GenerateMachineTranslationKey(
                                username = u,
                                password = p,
                            ),
                        )
                    }
                    component.reduce(MachineTranslationSettingsComponent.Intent.CloseDialog)
                },
            )
        }

        else -> Unit
    }
}
