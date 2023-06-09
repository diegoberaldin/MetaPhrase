package com.github.diegoberaldin.metaphrase.feature.translate.panel.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized

/**
 * Empty panel UI content. This is displayed when a panel section is not available (e.g. translation memory matches
 * for the base (source) language.
 *
 * @param modifier Modifier
 * @param onMinify on minify callback
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmptyPanelContent(
    modifier: Modifier = Modifier,
    onMinify: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
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
        Text(
            text = "message_panel_feature_not_available".localized(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
