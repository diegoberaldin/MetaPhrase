package com.github.diegoberaldin.metaphrase.feature.translate.messages.ui

import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.LocalTextContextMenu
import androidx.compose.foundation.text.TextContextMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.domain.spellcheck.data.SpellCheckCorrection

/**
 * Custom context menu to suggest corrections for the translation text field.
 *
 * @param active flag indicating whether this is the active segment
 * @param spellingErrors list of spelling errors detected
 * @param onSuggestionAccepted on suggestion accepted callback
 * @param onIgnoreWord on ignore word callback
 * @param onAddToGlossary on add term to glossary callback
 * @param content inner composable
 * @return
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SuggestCorrectionsForTextFieldContextMenu(
    active: Boolean = false,
    spellingErrors: List<SpellCheckCorrection> = emptyList(),
    onSuggestionAccepted: (String, IntRange) -> Unit,
    onIgnoreWord: (String) -> Unit,
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
                                newItems += ContextMenuItem("context_menu_ignore".localized(selection)) {
                                    onIgnoreWord(selection)
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
