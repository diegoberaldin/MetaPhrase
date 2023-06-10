package intro.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.ui.theme.Spacing
import intro.presentation.IntroComponent
import localized

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
            style = MaterialTheme.typography.h3,
        )
        Spacer(Modifier.height(Spacing.s))
        Text(
            text = "app_intro_empty".localized(),
            style = MaterialTheme.typography.h5,
        )
    }
}
