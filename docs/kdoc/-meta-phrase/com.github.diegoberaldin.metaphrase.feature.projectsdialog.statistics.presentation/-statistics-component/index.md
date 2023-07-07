---
title: StatisticsComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projectsdialog.statistics.presentation](../index.html)/[StatisticsComponent](index.html)



# StatisticsComponent



[jvm]\
interface [StatisticsComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[StatisticsComponent.Intent](-intent/index.html), [StatisticsComponent.UiState](-ui-state/index.html), [StatisticsComponent.Effect](-effect/index.html)&gt; 

Statistics component.



## Types


| Name | Summary |
|---|---|
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val items: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[StatisticsItem](../-statistics-item/index.html)&gt; = emptyList())<br>UI state. |


## Properties


| Name | Summary |
|---|---|
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[StatisticsComponent.Effect](-effect/index.html)&gt; |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Project ID. |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[StatisticsComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#-1441949422%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-1441949422%2FFunctions%2F2137835383)(intent: [StatisticsComponent.Intent](-intent/index.html)) |

