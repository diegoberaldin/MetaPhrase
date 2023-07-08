package com.github.diegoberaldin.metaphrase.feature.translate.messages.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing

@Composable
fun TranslateBox(
    color: Color,
    squareTopLeft: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shadowHeight = 3.dp
    Column {
        val bgShape = if (squareTopLeft) {
            RoundedCornerShape(
                topEnd = 4.dp,
                bottomStart = 4.dp,
                bottomEnd = 4.dp,
            )
        } else {
            RoundedCornerShape(4.dp)
        }
        Box(
            modifier = modifier
                .shadow(
                    elevation = shadowHeight,
                    ambientColor = color,
                    spotColor = color,
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = bgShape,
                )
                .border(
                    color = color,
                    width = 1.dp,
                    shape = bgShape,
                ),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(Spacing.xs),
            ) {
                content()
            }
        }
    }
    Spacer(Modifier.height(Spacing.xs))
}
