package com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.ui

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation.TranslationMemoryComponent
import java.awt.Cursor

/**
 * Translation memory UI content.
 *
 * @param component Component
 * @param onMinify on minify callback
 * @param modifier Modifier
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslationMemoryContent(
    component: TranslationMemoryComponent,
    onMinify: () -> Unit,
    modifier: Modifier = Modifier,
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
                text = "translation_memory_title".localized(),
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            if (uiState.units.isEmpty()) {
                item {
                    Text(
                        text = "message_no_item_to_display".localized(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
            itemsIndexed(uiState.units) { idx, unit ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    // source segment
                    Box(
                        modifier = Modifier.fillMaxWidth().background(
                            color = Color(0xFF666666),
                            shape = RoundedCornerShape(4.dp),
                        ).padding(all = Spacing.xxs),
                    ) {
                        Text(
                            text = unit.original?.text.orEmpty(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Spacer(modifier = Modifier.height(Spacing.xxs))

                    // target segment
                    Box(
                        modifier = Modifier.fillMaxWidth().background(
                            color = Color(0xFF666666).copy(alpha = 0.75f),
                            shape = RoundedCornerShape(4.dp),
                        ).padding(all = Spacing.xxs),
                    ) {
                        Text(
                            text = unit.segment.text,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = Spacing.xxxs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "translation_memory_similarity".localized(unit.similarity),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        )
                        if (unit.origin.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(Spacing.xs))
                            Text(
                                text = "translation_memory_origin".localized(unit.origin),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        // action buttons
                        CustomTooltipArea(text = "tooltip_copy_translation".localized()) {
                            Icon(
                                modifier = Modifier.size(18.dp).padding(Spacing.xxs).onClick {
                                    component.reduce(TranslationMemoryComponent.Intent.CopyTranslation(idx))
                                },
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}
