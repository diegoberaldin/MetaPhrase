package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SelectLanguageDropDown(
    expanded: Boolean,
    languages: List<LanguageModel>,
    onDismiss: () -> Unit,
    onSelected: (LanguageModel) -> Unit,
) {
    DropdownMenu(
        modifier = Modifier.width(160.dp).height(250.dp)
            .background(MaterialTheme.colorScheme.background)
            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
            .border(
                width = Dp.Hairline,
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(4.dp),
            ),
        expanded = expanded,
        onDismissRequest = {
            onDismiss()
        },
    ) {
        var hoveredValue by remember {
            mutableStateOf<LanguageModel?>(null)
        }
        languages.forEach { language ->
            DropdownMenuItem(
                modifier = Modifier
                    .background(color = if (language == hoveredValue) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .fillMaxWidth().height(30.dp)
                    .onPointerEvent(PointerEventType.Enter) { hoveredValue = language }
                    .onPointerEvent(PointerEventType.Exit) { hoveredValue = null }
                    .padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
                onClick = {
                    onSelected(language)
                },
                text = {
                    Text(
                        text = language.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (language == hoveredValue) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                    )
                },
            )
        }
    }
}
