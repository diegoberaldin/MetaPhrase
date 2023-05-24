package translate.ui.messagelist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import common.ui.theme.Indigo800
import common.ui.theme.Purple800
import common.ui.theme.Spacing
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageListContent(
    component: MessageListComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(component) {
        component.selectionEvents.onEach { index ->
            lazyListState.scrollToItem(index)
        }.launchIn(this)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(vertical = Spacing.s),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        itemsIndexed(items = uiState.units, key = { _, unit -> unit.segment.key }) { idx, unit ->
            val key = unit.segment.key
            val focusRequester = remember {
                FocusRequester()
            }
            LaunchedEffect(uiState.editingIndex) {
                if (uiState.editingIndex == idx) {
                    runCatching {
                        focusRequester.requestFocus()
                    }
                }
            }
            var value by remember(key1 = unit.segment.id, key2 = uiState.editingIndex == idx) {
                mutableStateOf(
                    TextFieldValue(
                        text = unit.segment.text,
                        selection = TextRange(unit.segment.text.length),
                    ),
                )
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                // Key label
                Text(
                    modifier = Modifier.background(
                        color = Purple800,
                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                    ).padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
                    text = key,
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground,
                )
                // Source segment
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            color = Purple800,
                            shape = RoundedCornerShape(topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp),
                        )
                        .padding(
                            start = Spacing.xs,
                            top = Spacing.xs + Spacing.xxs,
                            end = Spacing.xs,
                            bottom = Spacing.s,
                        )
                        .onClick {
                            if (uiState.isBaseLanguage) {
                                component.startEditing(idx)
                            }
                        },
                ) {
                    if (uiState.isBaseLanguage) {
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            textStyle = MaterialTheme.typography.caption.copy(color = Color.White),
                            cursorBrush = SolidColor(Color.White),
                            value = value,
                            enabled = uiState.isBaseLanguage && idx == uiState.editingIndex,
                            onValueChange = {
                                value = it
                                component.setSegmentText(it.text)
                            },
                        )
                    } else {
                        Text(
                            text = unit.original?.text.orEmpty(),
                            style = MaterialTheme.typography.caption,
                            color = Color.White,
                        )
                    }
                }
                // target segment
                if (!uiState.isBaseLanguage) {
                    Spacer(modifier = Modifier.height(Spacing.xxs))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(
                                color = Indigo800,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(
                                start = Spacing.xs,
                                top = Spacing.s,
                                end = Spacing.xs,
                                bottom = Spacing.s,
                            )
                            .onClick {
                                component.startEditing(idx)
                            },
                    ) {
                        BasicTextField(
                            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                            textStyle = MaterialTheme.typography.caption.copy(color = Color.White),
                            cursorBrush = SolidColor(Color.White),
                            value = value,
                            enabled = idx == uiState.editingIndex,
                            onValueChange = {
                                value = it
                                component.setSegmentText(it.text)
                            },
                        )
                    }
                }
            }
        }
    }
}
