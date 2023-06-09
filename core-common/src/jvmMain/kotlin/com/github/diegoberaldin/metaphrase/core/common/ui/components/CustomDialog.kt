package com.github.diegoberaldin.metaphrase.core.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

/**
 * Custom dialog.
 *
 * @param title Dialog title
 * @param message Dialog message
 * @param buttonTexts Texts for the dialog's buttons
 * @param onClose On close callback
 */
@Composable
fun CustomDialog(
    title: String,
    message: String,
    buttonTexts: List<String> = emptyList(),
    onClose: (Int?) -> Unit,
) {
    Dialog(
        title = title,
        state = rememberDialogState(width = 400.dp, height = Dp.Unspecified),
        onCloseRequest = {
            onClose(null)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(Spacing.s),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Row(
                modifier = Modifier.padding(Spacing.s),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                buttonTexts.forEachIndexed { index, text ->
                    Button(
                        modifier = Modifier.heightIn(max = 25.dp),
                        contentPadding = PaddingValues(0.dp),
                        onClick = {
                            onClose(index)
                        },
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
            }
        }
    }
}
