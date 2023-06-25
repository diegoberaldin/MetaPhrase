---
title: TranslationMemoryUiState
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation](../index.html)/[TranslationMemoryUiState](index.html)



# TranslationMemoryUiState



[jvm]\
data class [TranslationMemoryUiState](index.html)(val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList())

Translation memory UI state.



## Constructors


| | |
|---|---|
| [TranslationMemoryUiState](-translation-memory-ui-state.html) | [jvm]<br>constructor(isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList())<br>Create [TranslationMemoryUiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>flag indicating whether there is a background operation in progress |
| [units](units.html) | [jvm]<br>val [units](units.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt;<br>translation units to display |

