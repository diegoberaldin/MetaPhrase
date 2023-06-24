package com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.ui

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

/**
 * Term chip UI content.
 *
 * @param term term to show
 * @param backgroundColor background color
 * @param onDelete on delete callback
 */
@Composable
internal fun TermChip(
    term: GlossaryTermModel,
    backgroundColor: Color = Color(0xFF666666),
    onDelete: () -> Unit,
) {
    ContextMenuArea(
        items = {
            listOf(ContextMenuItem(label = "tooltip_delete".localized(), onClick = { onDelete() }))
        },
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = Spacing.xs)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp),
                ).padding(horizontal = Spacing.s, vertical = Spacing.xs),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = term.lemma,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}
