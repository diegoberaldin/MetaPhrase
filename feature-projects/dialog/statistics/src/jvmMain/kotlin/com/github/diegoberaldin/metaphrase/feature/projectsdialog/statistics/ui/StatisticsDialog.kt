package com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomProgressIndicator
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.MetaPhraseTheme
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.StatisticsComponent
import com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation.StatisticsItem

/**
 * UI content of the statistics dialog.
 *
 * @param component component
 * @param onClose on close callback
 */
@Composable
fun StatisticsDialog(
    component: StatisticsComponent,
    onClose: () -> Unit,
) {
    MetaPhraseTheme {
        Window(
            title = "dialog_title_statistics".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose()
            },
        ) {
            Surface(
                modifier = Modifier.size(600.dp, 400.dp).background(MaterialTheme.colorScheme.background),
            ) {
                val uiState by component.uiState.collectAsState()

                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(Spacing.m).weight(1f),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        items(uiState.items) { item ->
                            when (item) {
                                is StatisticsItem.Divider -> {
                                    Divider(modifier = Modifier.fillMaxWidth().padding(top = Spacing.m))
                                }

                                is StatisticsItem.Header -> {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }

                                is StatisticsItem.LanguageHeader -> {
                                    Text(
                                        modifier = Modifier.padding(top = Spacing.s),
                                        text = item.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onBackground,
                                    )
                                }

                                is StatisticsItem.TextRow -> {
                                    Row {
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = item.value,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                    }
                                }

                                is StatisticsItem.BarChartRow -> {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = item.title,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                        Spacer(modifier = Modifier.width(Spacing.s))
                                        CustomProgressIndicator(
                                            modifier = Modifier.weight(1f).height(20.dp),
                                            progress = item.value,
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose()
                            },
                        ) {
                            Text(
                                text = "button_close".localized(),
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
