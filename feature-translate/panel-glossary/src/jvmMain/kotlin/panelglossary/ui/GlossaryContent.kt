package panelglossary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import common.ui.components.CustomTooltipArea
import common.ui.theme.Spacing
import localized
import java.awt.Cursor

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
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
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
                Text(
                    text = term.lemma,
                    modifier = Modifier.background(
                        color = Color(0xFF666666),
                        shape = RoundedCornerShape(4.dp),
                    ).padding(start = Spacing.s, end = Spacing.s, bottom = Spacing.xs, top = Spacing.xxs),
                    style = MaterialTheme.typography.caption,
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                // target terms
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (targetTerm in associated) {
                        Text(
                            text = targetTerm.lemma,
                            modifier = Modifier.background(
                                color = Color(0xFF666666).copy(alpha = 0.75f),
                                shape = RoundedCornerShape(4.dp),
                            ).padding(start = Spacing.s, end = Spacing.s, bottom = Spacing.xxs, top = Spacing.xxs),
                            style = MaterialTheme.typography.caption,
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
