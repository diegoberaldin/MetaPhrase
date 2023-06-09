package panelvalidate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SpellingMistakesValidateContent(
    content: ValidationContent.SpellingMistakes,
    onItemSelected: (Int) -> Unit,
) {
    if (content.references.isEmpty()) {
        Text(
            text = "message_spelling_valid".localized(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground,
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Text(
                text = "message_spelling_invalid".localized(),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                item {
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
                            text = "column_invalid_spelling".localized(),
                            style = MaterialTheme.typography.caption.copy(fontSize = 9.sp),
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.9f),
                        )
                    }
                }
                itemsIndexed(content.references) { idx, reference ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(Spacing.xs)
                            .onClick {
                                onItemSelected(idx)
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
                            text = reference.mistakes.joinToString(", "),
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
