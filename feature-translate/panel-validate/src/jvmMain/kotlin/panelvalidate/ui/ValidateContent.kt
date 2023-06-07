package panelvalidate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.ui.components.CustomTooltipArea
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ValidateContent(
    component: InvalidSegmentComponent,
    onMinify: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "validation_title".localized(),
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

        when (uiState.stage) {
            InvalidSegmentStage.INITIAL -> Text(
                text = "No validation results, run a validation with the \"Validate\" toolbar action",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
            )

            InvalidSegmentStage.CONTENT -> {
                if (uiState.references.isEmpty()) {
                    Text(
                        text = "message_validation_valid".localized(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                } else {
                    Text(
                        text = "message_validation_invalid".localized(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        stickyHeader {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.xxxs),
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.75f),
                                    text = "column_invalid_key".localized(),
                                    style = MaterialTheme.typography.caption.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.9f),
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "column_invalid_missing".localized(),
                                    style = MaterialTheme.typography.caption.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.9f),
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "column_invalid_extra".localized(),
                                    style = MaterialTheme.typography.caption.copy(fontSize = 9.sp),
                                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.9f),
                                )
                            }
                        }
                        itemsIndexed(uiState.references) { idx, reference ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(Spacing.xs)
                                    .onClick {
                                        component.setCurrentIndex(idx)
                                    },
                                horizontalArrangement = Arrangement.spacedBy(Spacing.xxxs),
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.75f),
                                    text = reference.key,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = reference.missingPlaceholders.joinToString(", "),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = reference.extraPlaceholders.joinToString(", "),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
