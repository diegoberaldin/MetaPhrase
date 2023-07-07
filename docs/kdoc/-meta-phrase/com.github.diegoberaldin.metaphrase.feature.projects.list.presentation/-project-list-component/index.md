---
title: ProjectListComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.list.presentation](../index.html)/[ProjectListComponent](index.html)



# ProjectListComponent



[jvm]\
interface [ProjectListComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[ProjectListComponent.Intent](-intent/index.html), [ProjectListComponent.UiState](-ui-state/index.html), [ProjectListComponent.Effect](-effect/index.html)&gt; 

Recent project list component contract.



## Types


| Name | Summary |
|---|---|
| [DialogConfiguration](-dialog-configuration/index.html) | [jvm]<br>interface [DialogConfiguration](-dialog-configuration/index.html) : Parcelable<br>Available dialog configurations. |
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val projects: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)&gt; = emptyList())<br>Project list UI state. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[ProjectListComponent.DialogConfiguration](-dialog-configuration/index.html), *&gt;&gt; |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[ProjectListComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[ProjectListComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#211528863%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#211528863%2FFunctions%2F2137835383)(intent: [ProjectListComponent.Intent](-intent/index.html)) |

