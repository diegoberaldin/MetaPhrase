---
title: MessageListComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../index.html)/[MessageListComponent](index.html)



# MessageListComponent



[jvm]\
interface [MessageListComponent](index.html)

Message list component.



## Properties


| Name | Summary |
|---|---|
| [addToGlossaryEvents](add-to-glossary-events.html) | [jvm]<br>abstract val [addToGlossaryEvents](add-to-glossary-events.html): SharedFlow&lt;[AddToGlossaryEvent](../-add-to-glossary-event/index.html)&gt;<br>Events emitted when new terms should be added to the glossary (see [AddToGlossaryEvent](../-add-to-glossary-event/index.html)) |
| [editedSegment](edited-segment.html) | [jvm]<br>abstract val [editedSegment](edited-segment.html): StateFlow&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?&gt;<br>Currently edited segment |
| [isShowingProgress](is-showing-progress.html) | [jvm]<br>abstract val [isShowingProgress](is-showing-progress.html): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>Flag indicating whether a background operation is in progress |
| [paginationState](pagination-state.html) | [jvm]<br>abstract val [paginationState](pagination-state.html): StateFlow&lt;[MessageListPaginationState](../-message-list-pagination-state/index.html)&gt;<br>Message pagination UI state |
| [selectionEvents](selection-events.html) | [jvm]<br>abstract val [selectionEvents](selection-events.html): SharedFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt;<br>Event triggered with the index of the message being edited (needed to scroll the list programmatically) |
| [spellingErrors](spelling-errors.html) | [jvm]<br>abstract val [spellingErrors](spelling-errors.html): StateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt;&gt;<br>List of spelling error detected and the corresponding corrections |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[MessageListUiState](../-message-list-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [addToGlossarySource](add-to-glossary-source.html) | [jvm]<br>abstract fun [addToGlossarySource](add-to-glossary-source.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Opens the new glossary term dialog. |
| [changeSegmentText](change-segment-text.html) | [jvm]<br>abstract fun [changeSegmentText](change-segment-text.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Change segment text programmatically (intended for application initiated interaction). |
| [clearMessages](clear-messages.html) | [jvm]<br>abstract fun [clearMessages](clear-messages.html)()<br>Clear the message list. |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)()<br>Copy the base (source) message to the target field. |
| [deleteSegment](delete-segment.html) | [jvm]<br>abstract fun [deleteSegment](delete-segment.html)()<br>Delete the current segment. |
| [endEditing](end-editing.html) | [jvm]<br>abstract fun [endEditing](end-editing.html)()<br>End the editing operation. |
| [ignoreWordInSpelling](ignore-word-in-spelling.html) | [jvm]<br>abstract fun [ignoreWordInSpelling](ignore-word-in-spelling.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Ignore a given word in spelling for the current language adding it to a user defined dictionary. |
| [loadNextPage](load-next-page.html) | [jvm]<br>abstract fun [loadNextPage](load-next-page.html)()<br>Load the next page of messages from the DB. |
| [markAsTranslatable](mark-as-translatable.html) | [jvm]<br>abstract fun [markAsTranslatable](mark-as-translatable.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Mark a segment as translatable. |
| [moveToNext](move-to-next.html) | [jvm]<br>abstract fun [moveToNext](move-to-next.html)()<br>Move cursor to the next message. |
| [moveToPrevious](move-to-previous.html) | [jvm]<br>abstract fun [moveToPrevious](move-to-previous.html)()<br>Move cursor to the previous message. |
| [refresh](refresh.html) | [jvm]<br>abstract fun [refresh](refresh.html)()<br>Reload the message list. |
| [reloadMessages](reload-messages.html) | [jvm]<br>abstract fun [reloadMessages](reload-messages.html)(language: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Reload the message list. It should be called for the first loading operation, for subsequent ones the [refresh](refresh.html) method is enough |
| [scrollToMessage](scroll-to-message.html) | [jvm]<br>abstract fun [scrollToMessage](scroll-to-message.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Scroll to a given message. |
| [search](search.html) | [jvm]<br>abstract fun [search](search.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Search for messages matching the textual query. |
| [setEditingEnabled](set-editing-enabled.html) | [jvm]<br>abstract fun [setEditingEnabled](set-editing-enabled.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Enable or disable editing. |
| [setSegmentText](set-segment-text.html) | [jvm]<br>abstract fun [setSegmentText](set-segment-text.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the current segment text (intended for user initiated interaction). |
| [startEditing](start-editing.html) | [jvm]<br>abstract fun [startEditing](start-editing.html)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Start editing a given message. |

