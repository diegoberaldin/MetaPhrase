---
title: RootComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../index.html)/[RootComponent](index.html)



# RootComponent



[jvm]\
interface [RootComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[RootComponent.Intent](-intent/index.html), [RootComponent.UiState](-ui-state/index.html), [RootComponent.Effect](-effect/index.html)&gt; 

Root component.



## Types


| Name | Summary |
|---|---|
| [Config](-config/index.html) | [jvm]<br>interface [Config](-config/index.html) : Parcelable<br>Main screen content slot configuration. |
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available dialog configurations. |
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val activeProject: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isSaveEnabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Root UI state. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[RootComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Navigation slot for currently opened dialog. |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[RootComponent.Effect](-effect/index.html)&gt; |
| [main](main.html) | [jvm]<br>abstract val [main](main.html): Value&lt;ChildSlot&lt;[RootComponent.Config](-config/index.html), *&gt;&gt;<br>Navigation slot for the main content. |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[RootComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [hasUnsavedChanges](has-unsaved-changes.html) | [jvm]<br>abstract fun [hasUnsavedChanges](has-unsaved-changes.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether the current project has any unsaved changes. |
| [reduce](index.html#-1055230147%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#-1055230147%2FFunctions%2F2137835383)(intent: [RootComponent.Intent](-intent/index.html)) |

