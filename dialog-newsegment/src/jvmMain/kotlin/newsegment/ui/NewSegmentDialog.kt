package newsegment.ui

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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import common.ui.components.CustomTextField
import common.ui.theme.MetaPhraseTheme
import common.ui.theme.Spacing
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import localized
import newsegment.presentation.NewSegmentComponent
import java.awt.Cursor

@Composable
fun NewSegmentDialog(
    component: NewSegmentComponent,
    onClose: () -> Unit,
) {
    LaunchedEffect(component) {
        component.done.onEach {
            onClose?.invoke()
        }.launchIn(this)
    }
    MetaPhraseTheme {
        Window(
            title = "dialog_title_create_segment".localized(),
            state = rememberWindowState(width = Dp.Unspecified, height = Dp.Unspecified),
            resizable = false,
            onCloseRequest = {
                component.close()
            },
        ) {
            Surface(
                modifier = Modifier.size(400.dp, 200.dp).background(MaterialTheme.colors.background),
            ) {
                val uiState by component.uiState.collectAsState()
                val pointerIcon by remember(uiState.isLoading) {
                    if (uiState.isLoading) {
                        mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)))
                    } else {
                        mutableStateOf(PointerIcon(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)))
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerHoverIcon(pointerIcon)
                        .padding(vertical = Spacing.s, horizontal = Spacing.lHalf),
                ) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_segment_key".localized(),
                        value = uiState.key,
                        onValueChange = {
                            component.setKey(it)
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.keyError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        label = "create_segment_text".localized(),
                        value = uiState.text,
                        onValueChange = {
                            component.setText(it)
                        },
                    )
                    Text(
                        modifier = Modifier.padding(top = Spacing.xxs, start = Spacing.xxs),
                        text = uiState.textError,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    Row(
                        modifier = Modifier.padding(Spacing.s),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.close()
                            },
                        ) {
                            Text(text = "button_cancel".localized(), style = MaterialTheme.typography.button)
                        }
                        Button(
                            modifier = Modifier.heightIn(max = 25.dp),
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                                component.submit()
                            },
                        ) {
                            Text(text = "button_ok".localized(), style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
        }
    }
}