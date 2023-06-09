package com.github.diegoberaldin.metaphrase.feature.intro.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.intro.presentation.IntroComponent

/**
 * UI content of the intro (welcome) screen.
 *
 * @param component Component
 */
@Composable
fun MainEmptyContent(
    component: IntroComponent,
) {
    Column(
        modifier = Modifier.padding(horizontal = Spacing.s, vertical = Spacing.m),
        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
    ) {
        Text(
            text = "app_intro_title".localized(),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(Spacing.s))
        Text(
            text = "app_intro_empty".localized(),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
