---
title: ProjectsComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.presentation](../index.html)/[ProjectsComponent](index.html)



# ProjectsComponent



[jvm]\
interface [ProjectsComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[ProjectsComponent.Intent](-intent/index.html), [ProjectsComponent.UiState](-ui-state/index.html), [ProjectsComponent.Effect](-effect/index.html)&gt; 

Projects component contract.



## Types


| Name | Summary |
|---|---|
| [Config](-config/index.html) | [jvm]<br>interface [Config](-config/index.html) : Parcelable<br>Available screen configuration. |
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html) |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html) |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val activeProject: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>UI state. |


## Properties


| Name | Summary |
|---|---|
| [childStack](child-stack.html) | [jvm]<br>abstract val [childStack](child-stack.html): Value&lt;ChildStack&lt;[ProjectsComponent.Config](-config/index.html), *&gt;&gt;<br>Navigation stack for the project list/detail. |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[ProjectsComponent.Effect](-effect/index.html)&gt; |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[ProjectsComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#-590166268%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-590166268%2FFunctions%2F2137835383)(intent: [ProjectsComponent.Intent](-intent/index.html)) |

