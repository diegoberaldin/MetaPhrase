---
title: BrowseMemoryComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation](../index.html)/[BrowseMemoryComponent](index.html)



# BrowseMemoryComponent



[jvm]\
interface [BrowseMemoryComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[BrowseMemoryComponent.Intent](-intent/index.html), [BrowseMemoryComponent.UiState](-ui-state/index.html), [BrowseMemoryComponent.Effect](-effect/index.html)&gt; 

Browse memory component.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val sourceLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val availableSourceLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val targetLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val availableTargetLanguages: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; = emptyList(), val currentSearch: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val entries: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)&gt; = emptyList())<br>UI state from the TM content panel. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[BrowseMemoryComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[BrowseMemoryComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#-2109143678%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-2109143678%2FFunctions%2F2137835383)(intent: [BrowseMemoryComponent.Intent](-intent/index.html)) |

