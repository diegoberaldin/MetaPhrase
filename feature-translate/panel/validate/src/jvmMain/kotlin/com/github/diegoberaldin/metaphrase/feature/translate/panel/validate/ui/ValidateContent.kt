package com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data.ValidationContent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation.ValidateComponent

/**
 * UI content of the validate panel
 *
 * @param component Component
 * @param onMinify On minify callback
 * @param modifier Modifier
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ValidateContent(
    component: ValidateComponent,
    onMinify: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "validation_title".localized(),
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

        when (val content = uiState.content) {
            null -> Text(
                text = "message_no_validation".localized(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )

            is ValidationContent.InvalidPlaceholders -> Box(
                modifier = Modifier.weight(1f),
            ) {
                InvalidPlaceholderValidateContent(
                    content = content,
                    onItemSelected = {
                        component.reduce(ValidateComponent.Intent.SelectItem(it))
                    },
                )
            }

            is ValidationContent.SpellingMistakes -> Box(
                modifier = Modifier.weight(1f),
            ) {
                SpellingMistakesValidateContent(
                    content = content,
                    onItemSelected = {
                        component.reduce(ValidateComponent.Intent.SelectItem(it))
                    },
                )
            }
        }
    }
}
