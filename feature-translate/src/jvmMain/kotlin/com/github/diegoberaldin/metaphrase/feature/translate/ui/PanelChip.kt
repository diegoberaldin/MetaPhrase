package com.github.diegoberaldin.metaphrase.feature.translate.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.SelectedBackground
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

/**
 * Panel chip for bottom panel sections.
 *
 * @param title section title
 * @param isActive whether this is the active section
 * @param onActivate callback when chip activated
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PanelChip(
    title: String,
    isActive: Boolean = false,
    onActivate: () -> Unit,
) {
    Text(
        modifier = Modifier.background(
            color = SelectedBackground,
            shape = RoundedCornerShape(4.dp),
        )
            .padding(vertical = Spacing.xs, horizontal = Spacing.s)
            .onClick {
                onActivate()
            },
        text = title,
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onBackground.copy(
            alpha = if (isActive) 1f else 0.7f,
        ),
    )
}
