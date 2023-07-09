package com.github.diegoberaldin.metaphrase.feature.translate.messages.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.github.diegoberaldin.metaphrase.core.common.ui.components.CustomTooltipArea
import com.github.diegoberaldin.metaphrase.core.common.ui.theme.Spacing
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Message list UI content.
 *
 * @param component Component
 * @param modifier Modifier
 * @return
 */
@OptIn(ExperimentalFoundationApi::class, FlowPreview::class)
@Composable
fun MessageListContent(
    component: MessageListComponent,
    modifier: Modifier = Modifier,
) {
    val uiState by component.uiState.collectAsState()
    val lazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(component) {
        component.effects.filterIsInstance<MessageListComponent.Effect.Selection>()
            .debounce(250)
            .onEach { evt ->
                val index = evt.index
                if (index < lazyListState.firstVisibleItemIndex) {
                    lazyListState.scrollToItem(index = (index - 1).coerceAtLeast(0))
                } else {
                    lazyListState.animateScrollToItem(index = (index - 1).coerceAtLeast(0))
                }
                component.reduce(MessageListComponent.Intent.StartEditing(index))
            }.launchIn(this)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth().padding(vertical = Spacing.s),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(Spacing.s),
    ) {
        itemsIndexed(items = uiState.units) { idx, unit ->
            val key = unit.segment.key
            val focusRequester = remember {
                FocusRequester()
            }

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
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                        ).padding(horizontal = Spacing.xs, vertical = Spacing.xxs),
                        text = key,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondary,
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
                                        component.reduce(
                                            MessageListComponent.Intent.MarkAsTranslatable(
                                                value = !isTranslatable,
                                                key = key,
                                            ),
                                        )
                                    },
                                imageVector = if (isTranslatable) Icons.Default.LockOpen else Icons.Default.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                        Spacer(modifier = Modifier.width(Spacing.xxxs))
                    }
                }
                // Source segment
                TranslateBox(
                    color = MaterialTheme.colorScheme.secondary,
                    squareTopLeft = true,
                ) {
                    if (uiState.currentLanguage?.isBase == true) {
                        TranslateEditableField(
                            unit = unit,
                            focusRequester = focusRequester,
                            active = idx == uiState.editingIndex,
                            updateTextSwitch = uiState.updateTextSwitch,
                            enabled = uiState.editingEnabled,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            spellingErrors = uiState.spellingErrors,
                            onStartEditing = {
                                component.reduce(MessageListComponent.Intent.StartEditing(idx))
                            },
                            onTextChanged = {
                                component.reduce(MessageListComponent.Intent.SetSegmentText(it))
                            },
                            onAddToGlossary = { word ->
                                component.reduce(
                                    MessageListComponent.Intent.AddToGlossarySource(
                                        lemma = word,
                                        lang = uiState.currentLanguage?.code.orEmpty(),
                                    ),
                                )
                            },
                            onIgnoreWord = { word ->
                                component.reduce(
                                    MessageListComponent.Intent.IgnoreWordInSpelling(
                                        word = word,
                                    ),
                                )
                            },
                        )
                    } else {
                        Text(
                            text = unit.original?.text.orEmpty(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
                // target segment
                if (uiState.currentLanguage?.isBase == false) {
                    Spacer(modifier = Modifier.height(Spacing.xs))

                    TranslateBox(
                        color = MaterialTheme.colorScheme.primary,
                    ) {
                        TranslateEditableField(
                            unit = unit,
                            focusRequester = focusRequester,
                            active = idx == uiState.editingIndex,
                            updateTextSwitch = uiState.updateTextSwitch,
                            enabled = uiState.editingEnabled,
                            textColor = MaterialTheme.colorScheme.onBackground,
                            spellingErrors = uiState.spellingErrors,
                            onStartEditing = {
                                component.reduce(MessageListComponent.Intent.StartEditing(idx))
                            },
                            onTextChanged = {
                                component.reduce(MessageListComponent.Intent.SetSegmentText(it))
                            },
                            onAddToGlossary = { word ->
                                component.reduce(
                                    MessageListComponent.Intent.AddToGlossarySource(
                                        lemma = word,
                                        lang = uiState.currentLanguage?.code.orEmpty(),
                                    ),
                                )
                            },
                            onIgnoreWord = { word ->
                                component.reduce(
                                    MessageListComponent.Intent.IgnoreWordInSpelling(
                                        word = word,
                                    ),
                                )
                            },
                        )
                    }
                }
            }
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.xxs),
                contentAlignment = Alignment.Center,
            ) {
                if (uiState.canFetchMore && !uiState.isShowingGlobalProgress) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    if (!uiState.isLoading) {
                        component.reduce(MessageListComponent.Intent.LoadNextPage)
                    }
                }
            }
        }
    }
}

