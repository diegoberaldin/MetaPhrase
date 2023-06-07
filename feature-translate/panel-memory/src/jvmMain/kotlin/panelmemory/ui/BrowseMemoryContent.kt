package panelmemory.ui

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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import common.ui.components.CustomTooltipArea
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BrowseMemoryContent(
    component: BrowseMemoryComponent,
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
                text = "memory_content_title".localized(),
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

        BrowseMemoryTopControls(
            sourceLanguage = uiState.sourceLanguage,
            availableSourceLanguages = uiState.availableSourceLanguages,
            targetLanguage = uiState.targetLanguage,
            availableTargetLanguages = uiState.availableTargetLanguages,
            currentSearch = uiState.currentSearch,
            onSourceLanguageSelected = {
                component.setSourceLanguage(it)
            },
            onTargetLanguageSelected = {
                component.setTargetLanguage(it)
            },
            onSearchChanged = {
                component.setSearch(it)
            },
            onSearchFired = {
                component.onSearchFired()
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
                            style = MaterialTheme.typography.caption,
                            color = Color.White,
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
                            style = MaterialTheme.typography.caption,
                            color = Color.White,
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = Spacing.xxxs),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "translation_memory_origin".localized(entry.origin),
                            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp),
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.9f),
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        // action buttons
                        CustomTooltipArea(text = "tooltip_delete".localized()) {
                            Icon(
                                modifier = Modifier.size(18.dp).padding(Spacing.xxs).onClick {
                                    component.deleteEntry(idx)
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
