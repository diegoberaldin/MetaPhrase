package translationtranslationmemory.ui

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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.ui.components.CustomTooltipArea
import common.ui.theme.Purple800
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslationMemoryContent(
    component: TranslationMemoryComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        itemsIndexed(uiState.units) { idx, unit ->
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                // source segment
                Box(
                    modifier = Modifier.fillMaxWidth().background(
                        color = Purple800,
                        shape = RoundedCornerShape(topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                    ).padding(
                        start = Spacing.xs,
                        top = Spacing.xs + Spacing.xxs,
                        end = Spacing.xs,
                        bottom = Spacing.s,
                    ),
                ) {
                    Text(
                        text = unit.original?.text.orEmpty(),
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.xxs))

                // target segment
                Box(
                    modifier = Modifier.fillMaxWidth().background(
                        color = Purple800,
                        shape = RoundedCornerShape(topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                    ).padding(
                        start = Spacing.xs,
                        top = Spacing.xs + Spacing.xxs,
                        end = Spacing.xs,
                        bottom = Spacing.s,
                    ),
                ) {
                    Text(
                        text = unit.segment.text,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "translation_memory_similarity".localized(unit.similarity),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    // action buttons
                    CustomTooltipArea(text = "tooltip_copy_translation".localized()) {
                        Icon(
                            modifier = Modifier.size(22.dp).padding(Spacing.xxxs).onClick {
                                component.copyTranslation(idx)
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
