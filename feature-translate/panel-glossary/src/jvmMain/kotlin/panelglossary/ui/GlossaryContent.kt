package panelglossary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.ui.components.CustomTooltipArea
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun GlossaryContent(
    component: GlossaryComponent,
    modifier: Modifier = Modifier,
    onMinify: () -> Unit,
) {
    Column(
        modifier = modifier,
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
        val uiState by component.uiState.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            if (uiState.terms.isEmpty()) {
                item {
                    Text(
                        text = "message_no_item_to_display".localized(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
            }

            items(uiState.terms) { pair ->
                val term = pair.first
                val associated = pair.second
                // source term
                Text(
                    text = term.lemma,
                    modifier = Modifier.background(
                        color = Color(0xFF666666),
                        shape = RoundedCornerShape(4.dp),
                    ).padding(horizontal = Spacing.s, vertical = Spacing.xs),
                )
                // target terms
                FlowRow {
                    for (targetTerm in associated) {
                        Text(
                            text = targetTerm.lemma,
                            modifier = Modifier.background(
                                color = Color(0xFF666666).copy(alpha = 0.75f),
                                shape = RoundedCornerShape(4.dp),
                            ).padding(horizontal = Spacing.s, vertical = Spacing.xs),
                        )
                    }
                }
            }
        }
    }
}
