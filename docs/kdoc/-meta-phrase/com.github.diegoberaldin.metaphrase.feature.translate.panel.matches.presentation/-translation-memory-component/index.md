---
title: TranslationMemoryComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation](../index.html)/[TranslationMemoryComponent](index.html)



# TranslationMemoryComponent



[jvm]\
interface [TranslationMemoryComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[TranslationMemoryComponent.Intent](-intent/index.html), [TranslationMemoryComponent.UiState](-ui-state/index.html), [TranslationMemoryComponent.Effect](-effect/index.html)&gt; 

Translation memory component.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val units: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt; = emptyList())<br>Translation memory UI state. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[TranslationMemoryComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[TranslationMemoryComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#1725299291%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#1725299291%2FFunctions%2F2137835383)(intent: [TranslationMemoryComponent.Intent](-intent/index.html)) |

