package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.TranslationThemeRepository
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation.MachineTranslationComponent
import java.awt.Cursor

/**
 * Machine translation UI content.
 *
 * @param component Component
 * @param modifier Modifier
 * @param onMinify on minify callback
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MachineTranslationContent(
    component: MachineTranslationComponent,
    modifier: Modifier = Modifier,
    onMinify: () -> Unit,
) {
    val uiState by component.uiState.collectAsState()
    val pointerIcon by remember(uiState.isLoading) {
        if (uiState.isLoading) {
            mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)))
        } else {
            mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)))
        }
    }
    val translationThemeRepository: TranslationThemeRepository = getByInjection()
    val textStyle by translationThemeRepository.textStyle.collectAsState()

    Column(
        modifier = modifier.pointerHoverIcon(pointerIcon),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "machine_translation_title".localized(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            CustomTooltipArea(
                modifier = Modifier.align(Alignment.TopEnd),
                text = "tooltip_minify".localized(),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp).padding(top = 2.dp, bottom = 8.dp).onClick { onMinify() },
                    imageVector = Icons.Default.Minimize,
                    contentDescription = null,
                )
            }
        }
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .shadow(
                        elevation = 3.dp,
                        ambientColor = MaterialTheme.colorScheme.secondary,
                        spotColor = MaterialTheme.colorScheme.secondary,
                    )
                    .border(
                        color = MaterialTheme.colorScheme.secondary,
                        width = 1.dp,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(
                        start = Spacing.xs,
                        top = Spacing.s,
                        end = Spacing.xs,
                        bottom = Spacing.s,
                    ),
            ) {
                var textFieldValue by remember(uiState.updateTextSwitch) {
                    mutableStateOf(TextFieldValue(uiState.translation))
                }

                BasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = textStyle.copy(color = MaterialTheme.colorScheme.onBackground),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    value = textFieldValue,
                    maxLines = 5,
                    onValueChange = {
                        textFieldValue = it
                        component.reduce(MachineTranslationComponent.Intent.SetTranslation(it.text))
                    },
                )
            }
            Spacer(modifier = Modifier.height(Spacing.s))
            ButtonBar(
                modifier = Modifier.fillMaxWidth(),
                isEmpty = uiState.translation.isEmpty(),
                supportsContributions = uiState.supportsContributions,
                onShare = {
                    component.reduce(MachineTranslationComponent.Intent.Share)
                },
                onAccept = {
                    component.reduce(MachineTranslationComponent.Intent.InsertTranslation)
                },
                onDownload = {
                    component.reduce(MachineTranslationComponent.Intent.Retrieve)
                },
                onCopyTarget = {
                    component.reduce(MachineTranslationComponent.Intent.CopyTarget)
                },
            )
            Spacer(modifier = Modifier.height(Spacing.m))
        }
    }
}

@Composable
private fun ButtonBar(
    modifier: Modifier = Modifier,
    isEmpty: Boolean = false,
    supportsContributions: Boolean = false,
    onShare: () -> Unit = {},
    onAccept: () -> Unit = {},
    onCopyTarget: () -> Unit = {},
    onDownload: () -> Unit = {},
) {
    val iconModifier = Modifier.size(16.dp)
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        if (supportsContributions) {
            // insert button
            Button(
                modifier = Modifier.heightIn(max = 25.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onCopyTarget()
                },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.s),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                ) {
                    Text(
                        text = "button_copy_target".localized(),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                    )
                }
            }
            // share button
            Button(
                modifier = Modifier.heightIn(max = 25.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onShare()
                },
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Spacing.s),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                ) {
                    Text(
                        text = "button_share".localized(),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // insert button
        Button(
            modifier = Modifier.heightIn(max = 25.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onAccept()
            },
        ) {
            Row(
                modifier = Modifier.padding(horizontal = Spacing.s),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                Text(
                    text = "button_insert".localized(),
                    style = MaterialTheme.typography.labelLarge,
                )
                Icon(
                    modifier = iconModifier,
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            }
        }
        // get or update button
        Button(
            modifier = Modifier.heightIn(max = 25.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onDownload()
            },
        ) {
            Row(
                modifier = Modifier.padding(horizontal = Spacing.s),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                if (isEmpty) {
                    Text(
                        text = "button_retrieve".localized(),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Default.Download,
                        contentDescription = null,
                    )
                } else {
                    Text(
                        text = "button_refresh".localized(),
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Icon(
                        modifier = iconModifier,
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
