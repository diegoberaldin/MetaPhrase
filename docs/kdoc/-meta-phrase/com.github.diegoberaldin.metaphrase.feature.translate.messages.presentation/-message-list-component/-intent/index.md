---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../../index.html)/[MessageListComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [ReloadMessages](-reload-messages/index.html) |
| [Refresh](-refresh/index.html) |
| [LoadNextPage](-load-next-page/index.html) |
| [Search](-search/index.html) |
| [StartEditing](-start-editing/index.html) |
| [EndEditing](-end-editing/index.html) |
| [MoveToPrevious](-move-to-previous/index.html) |
| [MoveToNext](-move-to-next/index.html) |
| [SetSegmentText](-set-segment-text/index.html) |
| [ChangeSegmentText](-change-segment-text/index.html) |
| [CopyBase](-copy-base/index.html) |
| [DeleteSegment](-delete-segment/index.html) |
| [ScrollToMessage](-scroll-to-message/index.html) |
| [MarkAsTranslatable](-mark-as-translatable/index.html) |
| [SetEditingEnabled](-set-editing-enabled/index.html) |
| [ClearMessages](-clear-messages/index.html) |
| [AddToGlossarySource](-add-to-glossary-source/index.html) |
| [IgnoreWordInSpelling](-ignore-word-in-spelling/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddToGlossarySource](-add-to-glossary-source/index.html) | [jvm]<br>data class [AddToGlossarySource](-add-to-glossary-source/index.html)(val lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Opens the new glossary term dialog. |
| [ChangeSegmentText](-change-segment-text/index.html) | [jvm]<br>data class [ChangeSegmentText](-change-segment-text/index.html)(val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Change segment text programmatically (intended for application initiated interaction). |
| [ClearMessages](-clear-messages/index.html) | [jvm]<br>object [ClearMessages](-clear-messages/index.html) : [MessageListComponent.Intent](index.html)<br>Clear the message list. |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [MessageListComponent.Intent](index.html)<br>Copy the base (source) message to the target field. |
| [DeleteSegment](-delete-segment/index.html) | [jvm]<br>object [DeleteSegment](-delete-segment/index.html) : [MessageListComponent.Intent](index.html)<br>Delete the current segment. |
| [EndEditing](-end-editing/index.html) | [jvm]<br>object [EndEditing](-end-editing/index.html) : [MessageListComponent.Intent](index.html)<br>End the editing operation. |
| [IgnoreWordInSpelling](-ignore-word-in-spelling/index.html) | [jvm]<br>data class [IgnoreWordInSpelling](-ignore-word-in-spelling/index.html)(val word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Ignore a given word in spelling for the current language adding it to a user defined dictionary. |
| [LoadNextPage](-load-next-page/index.html) | [jvm]<br>object [LoadNextPage](-load-next-page/index.html) : [MessageListComponent.Intent](index.html)<br>Load the next page of messages from the DB. |
| [MarkAsTranslatable](-mark-as-translatable/index.html) | [jvm]<br>data class [MarkAsTranslatable](-mark-as-translatable/index.html)(val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Mark a segment as translatable. |
| [MoveToNext](-move-to-next/index.html) | [jvm]<br>object [MoveToNext](-move-to-next/index.html) : [MessageListComponent.Intent](index.html)<br>Move cursor to the next message. |
| [MoveToPrevious](-move-to-previous/index.html) | [jvm]<br>object [MoveToPrevious](-move-to-previous/index.html) : [MessageListComponent.Intent](index.html)<br>Move cursor to the previous message. |
| [Refresh](-refresh/index.html) | [jvm]<br>object [Refresh](-refresh/index.html) : [MessageListComponent.Intent](index.html)<br>Reload the message list. |
| [ReloadMessages](-reload-messages/index.html) | [jvm]<br>data class [ReloadMessages](-reload-messages/index.html)(val language: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), val filter: [TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MessageListComponent.Intent](index.html)<br>Reload the message list. It should be called for the first loading operation, for subsequent ones the [Refresh](-refresh/index.html) intent is enough |
| [ScrollToMessage](-scroll-to-message/index.html) | [jvm]<br>data class [ScrollToMessage](-scroll-to-message/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Scroll to a given message. |
| [Search](-search/index.html) | [jvm]<br>data class [Search](-search/index.html)(val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Search for messages matching the textual query. |
| [SetEditingEnabled](-set-editing-enabled/index.html) | [jvm]<br>data class [SetEditingEnabled](-set-editing-enabled/index.html)(val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [MessageListComponent.Intent](index.html)<br>Enable or disable editing. |
| [SetSegmentText](-set-segment-text/index.html) | [jvm]<br>data class [SetSegmentText](-set-segment-text/index.html)(val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Intent](index.html)<br>Set the current segment text (intended for user initiated interaction). |
| [StartEditing](-start-editing/index.html) | [jvm]<br>data class [StartEditing](-start-editing/index.html)(val index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MessageListComponent.Intent](index.html)<br>Start editing a given message. |

