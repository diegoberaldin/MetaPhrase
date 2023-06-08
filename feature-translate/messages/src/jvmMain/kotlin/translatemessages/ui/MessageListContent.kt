package translatemessages.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import common.ui.components.CustomTooltipArea
import common.ui.theme.Indigo800
import common.ui.theme.Purple800
import common.ui.theme.Spacing
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import localized

@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun MessageListContent(
    component: MessageListComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(component) {
        component.selectionEvents.debounce(500).onEach { index ->
            if (index < lazyListState.firstVisibleItemIndex) {
                lazyListState.scrollToItem(index)
            } else {
                lazyListState.animateScrollToItem(index = index)
            }
        }.launchIn(this)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(vertical = Spacing.s),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        itemsIndexed(
            items = uiState.units,
            key = { _, unit -> unit.segment.key + uiState.currentLanguage?.code.orEmpty() },
        ) { idx, unit ->
            val key = unit.segment.key
            val focusRequester = remember {
                FocusRequester()
            }
            val focusManager = LocalFocusManager.current
            LaunchedEffect(uiState.editingIndex) {
                runCatching {
                    if (uiState.editingIndex == idx) {
                        focusRequester.requestFocus()
                    } else if (uiState.editingIndex == null) {
                        focusManager.clearFocus()
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                // Key label
                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        modifier = Modifier.background(
                            color = Purple800,
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                        ).padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
                        text = key,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground,
                    )
                    // only segments in the base language can be marked as untranslatable
                    if (uiState.currentLanguage?.isBase == true) {
                        Spacer(modifier = Modifier.weight(1f))
                        val isTranslatable = unit.segment.translatable
                        CustomTooltipArea(
                            text = if (isTranslatable) "tooltip_make_untranslatable".localized() else "tooltip_make_translatable".localized(),
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp)
                                    .padding(4.dp)
                                    .onClick {
                                        component.markAsTranslatable(value = !isTranslatable, key = key)
                                    },
                                imageVector = if (isTranslatable) Icons.Default.LockOpen else Icons.Default.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                            )
                        }
                        Spacer(modifier = Modifier.width(Spacing.xxxs))
                    }
                }
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
                        ),
                ) {
                    if (uiState.currentLanguage?.isBase == true) {
                        val spellingErrors by component.spellingErrors.collectAsState()
                        TranslateEditableField(
                            unit = unit,
                            focusRequester = focusRequester,
                            active = idx == uiState.editingIndex,
                            updateTextSwitch = uiState.updateTextSwitch,
                            enabled = uiState.editingEnabled,
                            spellingErrors = spellingErrors,
                            onStartEditing = {
                                component.startEditing(idx)
                            },
                            onTextChanged = {
                                component.setSegmentText(it)
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
                if (uiState.currentLanguage?.isBase == false) {
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
                            ),
                    ) {
                        val spellingErrors by component.spellingErrors.collectAsState()
                        TranslateEditableField(
                            unit = unit,
                            focusRequester = focusRequester,
                            active = idx == uiState.editingIndex,
                            updateTextSwitch = uiState.updateTextSwitch,
                            enabled = uiState.editingEnabled,
                            spellingErrors = spellingErrors,
                            onStartEditing = {
                                component.startEditing(idx)
                            },
                            onTextChanged = {
                                component.setSegmentText(it)
                            },
                        )
                    }
                }
            }
        }
    }
}

