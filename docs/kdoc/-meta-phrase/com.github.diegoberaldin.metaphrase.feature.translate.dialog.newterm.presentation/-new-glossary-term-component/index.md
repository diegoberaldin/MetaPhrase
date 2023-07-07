---
title: NewGlossaryTermComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](../index.html)/[NewGlossaryTermComponent](index.html)



# NewGlossaryTermComponent

interface [NewGlossaryTermComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](-intent/index.html), [NewGlossaryTermComponent.UiState](-ui-state/index.html), [NewGlossaryTermComponent.Effect](-effect/index.html)&gt; 

New glossary term component contract.



#### Inheritors


| |
|---|
| [DefaultNewGlossaryTermComponent](../-default-new-glossary-term-component/index.html) |


## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html) |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val sourceTerm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val sourceTermError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetTerm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetTermError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>New glossary term UI state. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[NewGlossaryTermComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[NewGlossaryTermComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#530473964%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#530473964%2FFunctions%2F2137835383)(intent: [NewGlossaryTermComponent.Intent](-intent/index.html)) |

