package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.ValidationContent

/**
 * UI content of the placeholder validation panel.
 *
 * @param content Content
 * @param onItemSelected On item selected callback
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun InvalidPlaceholderValidateContent(
    content: ValidationContent.InvalidPlaceholders,
    onItemSelected: (Int) -> Unit,
) {
    if (content.references.isEmpty()) {
        Text(
            text = "message_validation_valid".localized(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
        ) {
            Text(
                text = "message_validation_invalid".localized(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
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
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "column_invalid_missing".localized(),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "column_invalid_extra".localized(),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
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
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = reference.missingPlaceholders.joinToString(", "),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = reference.extraPlaceholders.joinToString(", "),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}
