---
title: NewSegmentComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation](../index.html)/[NewSegmentComponent](index.html)



# NewSegmentComponent



[jvm]\
interface [NewSegmentComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[NewSegmentComponent.Intent](-intent/index.html), [NewSegmentComponent.UiState](-ui-state/index.html), [NewSegmentComponent.Effect](-effect/index.html)&gt; 

New segment component contract.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val keyError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val textError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>New segment dialog UI state. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[NewSegmentComponent.Effect](-effect/index.html)&gt; |
| [language](language.html) | [jvm]<br>abstract var [language](language.html): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)<br>Language for which the message should be added |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Current project ID |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[NewSegmentComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#905360120%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#905360120%2FFunctions%2F2137835383)(intent: [NewSegmentComponent.Intent](-intent/index.html)) |

