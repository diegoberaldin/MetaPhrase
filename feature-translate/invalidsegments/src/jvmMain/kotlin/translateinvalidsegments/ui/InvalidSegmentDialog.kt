package translateinvalidsegments.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import common.ui.theme.MetaPhraseTheme
import common.ui.theme.Spacing
import localized

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvalidSegmentDialog(
    component: InvalidSegmentComponent,
    onClose: () -> Unit,
) {
    MetaPhraseTheme {
        Window(
            title = "dialog_title_invalid_segments".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                onClose()
            },
        ) {
            Surface(
                modifier = Modifier.size(600.dp, 400.dp).background(MaterialTheme.colors.background),
            ) {
                val uiState by component.uiState.collectAsState()

                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = Spacing.m, vertical = Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s),
                ) {
                    Text(
                        text = "message_validation_invalid".localized(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        stickyHeader {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.xxxs),
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.75f),
                                    text = "column_invalid_key".localized(),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "column_invalid_missing".localized(),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "column_invalid_extra".localized(),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                )
                            }
                        }
                        itemsIndexed(uiState.references) { idx, reference ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(Spacing.xs)
                                    .onClick {
                                        component.setCurrentIndex(idx)
                                    },
                                horizontalArrangement = Arrangement.spacedBy(Spacing.xxxs),
                            ) {
                                Text(
                                    modifier = Modifier.weight(0.75f),
                                    text = reference.key,
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = reference.missingPlaceholders.joinToString(", "),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = reference.extraPlaceholders.joinToString(", "),
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onBackground,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                onClose()
                            },
                        ) {
                            Text(text = "button_close".localized(), style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
        }
    }
}
