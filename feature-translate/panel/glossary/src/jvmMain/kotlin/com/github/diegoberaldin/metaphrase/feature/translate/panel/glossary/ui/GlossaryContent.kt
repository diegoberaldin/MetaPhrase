package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation.GlossaryComponent
import java.awt.Cursor

/**
 * Glossary UI content.
 *
 * @param component Component
 * @param modifier Modifier
 * @param onMinify on minify callback
 * @return
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun GlossaryContent(
    component: GlossaryComponent,
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

    Column(
        modifier = modifier.pointerHoverIcon(pointerIcon),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "glossary_title".localized(),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            itemsIndexed(uiState.terms) { idx, pair ->
                val term = pair.first
                val associated = pair.second
                // source term
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (uiState.sourceFlag.isNotEmpty()) {
                        Text(
                            text = uiState.sourceFlag,
                            style = MaterialTheme.typography.caption,
                        )
                    }
                    TermChip(
                        term = term,
                        onDelete = {
                            component.deleteTerm(term)
                        },
                    )
                }

                if (!uiState.isBaseLanguage) {
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    // target terms
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (uiState.targetFlag.isNotEmpty()) {
                            Text(
                                text = uiState.targetFlag,
                                style = MaterialTheme.typography.caption,
                            )
                        }

                        for (targetTerm in associated) {
                            TermChip(
                                term = targetTerm,
                                backgroundColor = Color(0xFF666666).copy(alpha = 0.75f),
                                onDelete = {
                                    component.deleteTerm(targetTerm)
                                },
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                        ) {
                            CustomTooltipArea(
                                text = "tooltip_new_target_term".localized(),
                            ) {
                                Box(
                                    // at least 200.dp so that it is never unexpanded at the end and expanded on another
                                    modifier = Modifier.widthIn(min = 200.dp),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    GlossaryAddButton(
                                        backgroundColor = Color(0xFF666666).copy(alpha = 0.75f),
                                        onAddTerm = {
                                            val sourceTerm = uiState.terms[idx].first
                                            component.addTargetTerm(lemma = it, source = sourceTerm)
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    CustomTooltipArea(
                        text = "tooltip_new_source_term".localized(),
                    ) {
                        GlossaryAddButton(
                            onAddTerm = {
                                component.addSourceTerm(it)
                            },
                        )
                    }
                }
            }
        }
    }
}
