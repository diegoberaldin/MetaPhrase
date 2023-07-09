package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LanguageCell(
    language: LanguageModel,
    onSelected: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
            shape = RoundedCornerShape(4.dp),
        ).padding(vertical = Spacing.xs, horizontal = Spacing.s)
            .onClick { onSelected() },
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = language.name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.weight(1f))
        if (language.isBase) {
            CustomTooltipArea(
                text = "tooltip_base_language".localized(),
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(2.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
        CustomTooltipArea(
            text = "tooltip_delete".localized(),
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(2.dp)
                    .onClick {
                        onDeleteClicked()
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
