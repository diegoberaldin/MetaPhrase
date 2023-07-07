---
title: ValidateComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../index.html)/[ValidateComponent](index.html)



# ValidateComponent



[jvm]\
interface [ValidateComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[ValidateComponent.Intent](-intent/index.html), [ValidateComponent.UiState](-ui-state/index.html), [ValidateComponent.Effect](-effect/index.html)&gt; 

Validation component.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val content: [ValidationContent](../../com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data/-validation-content/index.html)? = null)<br>UI state of the validation panel. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[ValidateComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[ValidateComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#1275246274%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#1275246274%2FFunctions%2F2137835383)(intent: [ValidateComponent.Intent](-intent/index.html)) |

