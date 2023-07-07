---
title: IntroComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.intro.presentation](../index.html)/[IntroComponent](index.html)



# IntroComponent



[jvm]\
interface [IntroComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[IntroComponent.Intent](-intent/index.html), [IntroComponent.UiState](-ui-state/index.html), [IntroComponent.Effect](-effect/index.html)&gt; 

Intro component contract.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>object [UiState](-ui-state/index.html)<br>UI state. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[IntroComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[IntroComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#-1603250236%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-1603250236%2FFunctions%2F2137835383)(intent: [IntroComponent.Intent](-intent/index.html)) |

