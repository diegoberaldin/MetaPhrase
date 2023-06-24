package com.github.diegoberaldin.metaphrase.core.common.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

/**
 * Custom tooltip area.
 *
 * @param text Text to show in the tooltip
 * @param modifier Modifier
 * @param content Content inner composable
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTooltipArea(
    text: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TooltipArea(
        modifier = modifier,
        tooltip = {
            Text(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp))
                    .padding(Spacing.s),
                text = text,
                style = MaterialTheme.typography.caption,
                color = Color.White,
            )
        },
        content = content,
    )
}
