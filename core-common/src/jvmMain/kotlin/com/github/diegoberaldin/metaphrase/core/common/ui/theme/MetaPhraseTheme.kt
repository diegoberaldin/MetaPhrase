package com.github.diegoberaldin.metaphrase.core.common.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MetaPhraseTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        content = content,
    )
}
