---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../../index.html)/[MessageListComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList(), val editingIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, val currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val editingEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val canFetchMore: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isShowingGlobalProgress: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val spellingErrors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt; = emptyList())

Message list UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList(), editingIndex: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null, currentLanguage: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, editingEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, updateTextSwitch: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, canFetchMore: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true, isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isShowingGlobalProgress: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, spellingErrors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt; = emptyList())<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [canFetchMore](can-fetch-more.html) | [jvm]<br>val [canFetchMore](can-fetch-more.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true<br>flag indicating whether there are more messages to fetch |
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null<br>current language |
| [editingEnabled](editing-enabled.html) | [jvm]<br>val [editingEnabled](editing-enabled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true<br>flag indicating whether editing should be allowed |
| [editingIndex](editing-index.html) | [jvm]<br>val [editingIndex](editing-index.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)? = null<br>index of the message being edited |
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether loading is in progress |
| [isShowingGlobalProgress](is-showing-global-progress.html) | [jvm]<br>val [isShowingGlobalProgress](is-showing-global-progress.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether a global background operation is in progress |
| [spellingErrors](spelling-errors.html) | [jvm]<br>val [spellingErrors](spelling-errors.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt;<br>list of spelling error detected and the corresponding corrections |
| [units](units.html) | [jvm]<br>val [units](units.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt;<br>list of translation units |
| [updateTextSwitch](update-text-switch.html) | [jvm]<br>val [updateTextSwitch](update-text-switch.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag to trigger text updates for the current segment programmatically |

