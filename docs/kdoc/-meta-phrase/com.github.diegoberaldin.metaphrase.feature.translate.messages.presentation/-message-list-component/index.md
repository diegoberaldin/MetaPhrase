---
title: MessageListComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../index.html)/[MessageListComponent](index.html)



# MessageListComponent



[jvm]\
interface [MessageListComponent](index.html)



## Properties


| Name | Summary |
|---|---|
| [addToGlossaryEvents](add-to-glossary-events.html) | [jvm]<br>abstract val [addToGlossaryEvents](add-to-glossary-events.html): SharedFlow&lt;[AddToGlossaryEvent](../-add-to-glossary-event/index.html)&gt; |
| [editedSegment](edited-segment.html) | [jvm]<br>abstract val [editedSegment](edited-segment.html): StateFlow&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?&gt; |
| [isShowingProgress](is-showing-progress.html) | [jvm]<br>abstract val [isShowingProgress](is-showing-progress.html): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [paginationState](pagination-state.html) | [jvm]<br>abstract val [paginationState](pagination-state.html): StateFlow&lt;[MessageLisPaginationState](../-message-lis-pagination-state/index.html)&gt; |
| [selectionEvents](selection-events.html) | [jvm]<br>abstract val [selectionEvents](selection-events.html): SharedFlow&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)&gt; |
| [spellingErrors](spelling-errors.html) | [jvm]<br>abstract val [spellingErrors](spelling-errors.html): StateFlow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck/-spell-check-correction/index.html)&gt;&gt; |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[MessageListUiState](../-message-list-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addToGlossarySource](add-to-glossary-source.html) | [jvm]<br>abstract fun [addToGlossarySource](add-to-glossary-source.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [changeSegmentText](change-segment-text.html) | [jvm]<br>abstract fun [changeSegmentText](change-segment-text.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [clearMessages](clear-messages.html) | [jvm]<br>abstract fun [clearMessages](clear-messages.html)() |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)() |
| [deleteSegment](delete-segment.html) | [jvm]<br>abstract fun [deleteSegment](delete-segment.html)() |
| [endEditing](end-editing.html) | [jvm]<br>abstract fun [endEditing](end-editing.html)() |
| [ignoreWordInSpelling](ignore-word-in-spelling.html) | [jvm]<br>abstract fun [ignoreWordInSpelling](ignore-word-in-spelling.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [loadNextPage](load-next-page.html) | [jvm]<br>abstract fun [loadNextPage](load-next-page.html)() |
| [markAsTranslatable](mark-as-translatable.html) | [jvm]<br>abstract fun [markAsTranslatable](mark-as-translatable.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [moveToNext](move-to-next.html) | [jvm]<br>abstract fun [moveToNext](move-to-next.html)() |
| [moveToPrevious](move-to-previous.html) | [jvm]<br>abstract fun [moveToPrevious](move-to-previous.html)() |
| [refresh](refresh.html) | [jvm]<br>abstract fun [refresh](refresh.html)() |
| [reloadMessages](reload-messages.html) | [jvm]<br>abstract fun [reloadMessages](reload-messages.html)(language: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [scrollToMessage](scroll-to-message.html) | [jvm]<br>abstract fun [scrollToMessage](scroll-to-message.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [search](search.html) | [jvm]<br>abstract fun [search](search.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setEditingEnabled](set-editing-enabled.html) | [jvm]<br>abstract fun [setEditingEnabled](set-editing-enabled.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [setSegmentText](set-segment-text.html) | [jvm]<br>abstract fun [setSegmentText](set-segment-text.html)(text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [startEditing](start-editing.html) | [jvm]<br>abstract fun [startEditing](start-editing.html)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

