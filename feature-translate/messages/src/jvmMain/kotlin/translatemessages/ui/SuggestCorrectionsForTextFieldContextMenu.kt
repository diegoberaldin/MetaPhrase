package translatemessages.ui

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.LocalTextContextMenu
import androidx.compose.foundation.text.TextContextMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import localized
import spellcheck.SpellCheckCorrection

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SuggestCorrectionsForTextFieldContextMenu(
    active: Boolean = false,
    spellingErrors: List<SpellCheckCorrection> = emptyList(),
    onSuggestionAccepted: (String, IntRange) -> Unit,
    onAddToGlossary: (String) -> Unit,
    content: @Composable () -> Unit,
) {
    val textMenu = LocalTextContextMenu.current
    CompositionLocalProvider(
        LocalTextContextMenu provides object : TextContextMenu {
            @Composable
            override fun Area(
                textManager: TextContextMenu.TextManager,
                state: ContextMenuState,
                content: @Composable () -> Unit,
            ) {
                ContextMenuDataProvider(
                    items = {
                        val newItems = mutableListOf<ContextMenuItem>()
                        val selection = textManager.selectedText.trim().toString()
                        if (active && selection.isNotEmpty()) {
                            val spellingCorrection = spellingErrors.firstOrNull { it.value == selection }

                            // suggestions from spellcheck
                            if (spellingCorrection != null) {
                                newItems += spellingCorrection.suggestions.map { suggestedWord ->
                                    ContextMenuItem("context_menu_insert_suggestion".localized(suggestedWord)) {
                                        onSuggestionAccepted(suggestedWord, spellingCorrection.indices)
                                    }
                                }
                            }

                            // add to glossary option
                            newItems += ContextMenuItem("context_menu_add_glossary".localized()) {
                                onAddToGlossary(selection)
                            }
                        }
                        newItems
                    },
                ) {
                    textMenu.Area(textManager, state, content = content)
                }
            }
        },
        content = content,
    )
}