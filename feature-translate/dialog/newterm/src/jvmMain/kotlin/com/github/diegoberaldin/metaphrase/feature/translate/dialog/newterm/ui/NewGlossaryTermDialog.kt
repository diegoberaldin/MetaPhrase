package com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.ui

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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTextField
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.GlossaryTermPair
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.NewGlossaryTermComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * UI content of the new glossary term dialog. This dialog is shown whenever a new glossary term is being inserted for
 * a target language. In this case the target term should be pre-filled with the user selection and the source term must
 * be inserted to populate the glossary.
 *
 * @param targetTerm target term
 * @param component component
 * @param onClose on close callback
 */
@Composable
fun NewGlossaryTermDialog(
    targetTerm: String? = null,
    component: NewGlossaryTermComponent,
    onClose: ((GlossaryTermPair?) -> Unit)? = null,
) {
    LaunchedEffect(component) {
        component.reduce(NewGlossaryTermComponent.Intent.SetTargetTerm(targetTerm.orEmpty()))
        component.effects.onEach {
            when (it) {
                is NewGlossaryTermComponent.Effect.Done -> onClose?.invoke(it.pair)
            }
        }.launchIn(this)
    }
    MetaPhraseTheme {
        Window(
            title = "dialog_title_create_glossary_term".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose?.invoke(null)
            },
        ) {
            Surface(
                modifier = Modifier.size(400.dp, 200.dp).background(MaterialTheme.colors.background),
            ) {
                val uiState by component.uiState.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_glossary_source_term".localized(),
                        value = uiState.sourceTerm,
                        onValueChange = {
                            component.reduce(NewGlossaryTermComponent.Intent.SetSourceTerm(targetTerm.orEmpty()))
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.sourceTermError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_glossary_target_term".localized(),
                        value = uiState.targetTerm,
                        onValueChange = {
                            component.reduce(NewGlossaryTermComponent.Intent.SetTargetTerm(targetTerm.orEmpty()))
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.targetTermError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose?.invoke(null)
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.button)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.reduce(NewGlossaryTermComponent.Intent.Submit)
                            },
                        ) {
                            Text(text = "button_ok".localized(), style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
        }
    }
}
