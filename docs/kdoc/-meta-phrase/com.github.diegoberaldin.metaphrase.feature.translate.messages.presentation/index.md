---
title: com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [AddToGlossaryEvent](-add-to-glossary-event/index.html) | [jvm]<br>data class [AddToGlossaryEvent](-add-to-glossary-event/index.html)(val lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [MessageLisPaginationState](-message-lis-pagination-state/index.html) | [jvm]<br>data class [MessageLisPaginationState](-message-lis-pagination-state/index.html)(val canFetchMore: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [MessageListComponent](-message-list-component/index.html) | [jvm]<br>interface [MessageListComponent](-message-list-component/index.html) |
| [MessageListUiState](-message-list-ui-state/index.html) | [jvm]<br>data class [MessageListUiState](-message-list-ui-state/index.html)(val units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList(), val editingIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val currentLanguage: [LanguageModel](../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val editingEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |

