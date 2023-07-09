package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation.BrowseMemoryComponent

/**
 * UI content of the TM content panel.
 *
 * @param component Component
 * @param modifier Modifier
 * @param onMinify on minify callback
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BrowseMemoryContent(
    component: BrowseMemoryComponent,
    modifier: Modifier = Modifier,
    onMinify: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "memory_content_title".localized(),
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
        val uiState by component.uiState.collectAsState()

        BrowseMemoryTopControls(
            sourceLanguage = uiState.sourceLanguage,
            availableSourceLanguages = uiState.availableSourceLanguages,
            targetLanguage = uiState.targetLanguage,
            availableTargetLanguages = uiState.availableTargetLanguages,
            currentSearch = uiState.currentSearch,
            onSourceLanguageSelected = {
                component.reduce(BrowseMemoryComponent.Intent.SetSourceLanguage(it))
            },
            onTargetLanguageSelected = {
                component.reduce(BrowseMemoryComponent.Intent.SetTargetLanguage(it))
            },
            onSearchChanged = {
                component.reduce(BrowseMemoryComponent.Intent.SetSearch(it))
            },
            onSearchFired = {
                component.reduce(BrowseMemoryComponent.Intent.OnSearchFired)
            },
        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.s),
        ) {
            itemsIndexed(uiState.entries) { idx, entry ->
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
                            text = entry.sourceText,
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
                            text = entry.targetText,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = Spacing.xxxs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "translation_memory_origin".localized(entry.origin),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        // action buttons
                        CustomTooltipArea(text = "tooltip_delete".localized()) {
                            Icon(
                                modifier = Modifier.size(18.dp).padding(Spacing.xxs).onClick {
                                    component.reduce(BrowseMemoryComponent.Intent.DeleteEntry(idx))
                                },
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}
