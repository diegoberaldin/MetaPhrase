package translate.ui

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
import common.ui.theme.SelectedBackground
import common.ui.theme.Spacing

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
