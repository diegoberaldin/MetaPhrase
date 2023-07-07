---
title: TranslateComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../index.html)/[TranslateComponent](index.html)



# TranslateComponent



[jvm]\
interface [TranslateComponent](index.html) : [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[TranslateComponent.Intent](-intent/index.html), [TranslateComponent.UiState](-ui-state/index.html), [TranslateComponent.Effect](-effect/index.html)&gt; 

Translate component.



## Types


| Name | Summary |
|---|---|
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available dialog configurations for the [dialog](dialog.html) slot. |
| [Effect](-effect/index.html) | [jvm]<br>interface [Effect](-effect/index.html)<br>Effects. |
| [Intent](-intent/index.html) | [jvm]<br>interface [Intent](-intent/index.html)<br>View intents. |
| [MessageListConfig](-message-list-config/index.html) | [jvm]<br>object [MessageListConfig](-message-list-config/index.html) : Parcelable<br>Slot configuration for the message list. This is the only config value available in the [messageList](message-list.html) slot. |
| [PanelConfig](-panel-config/index.html) | [jvm]<br>interface [PanelConfig](-panel-config/index.html) : Parcelable<br>Available configurations for the [panel](panel.html) slot. |
| [ToolbarConfig](-toolbar-config/index.html) | [jvm]<br>object [ToolbarConfig](-toolbar-config/index.html) : Parcelable<br>Slot configuration for the translation toolbar. This is the only config value available in the [toolbar](toolbar.html) slot. |
| [UiState](-ui-state/index.html) | [jvm]<br>data class [UiState](-ui-state/index.html)(val project: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? = null, val unitCount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, val needsSaving: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isEditing: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val currentLanguage: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? = null)<br>UI state for the translation editor. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[TranslateComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Navigation slot for the dialogs |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>abstract val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[TranslateComponent.Effect](-effect/index.html)&gt; |
| [messageList](message-list.html) | [jvm]<br>abstract val [messageList](message-list.html): Value&lt;ChildSlot&lt;[TranslateComponent.MessageListConfig](-message-list-config/index.html), [MessageListComponent](../../com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation/-message-list-component/index.html)&gt;&gt;<br>Navigation slot for the message list |
| [panel](panel.html) | [jvm]<br>abstract val [panel](panel.html): Value&lt;ChildSlot&lt;[TranslateComponent.PanelConfig](-panel-config/index.html), *&gt;&gt;<br>Navigation slot for the bottom panel |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Current project ID |
| [toolbar](toolbar.html) | [jvm]<br>abstract val [toolbar](toolbar.html): Value&lt;ChildSlot&lt;[TranslateComponent.ToolbarConfig](-toolbar-config/index.html), [TranslateToolbarComponent](../../com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation/-translate-toolbar-component/index.html)&gt;&gt;<br>Navigation slot for the toolbar |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>abstract val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[TranslateComponent.UiState](-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](index.html#1487837956%2FFunctions%2F2137835383) | [jvm]<br>abstract fun [reduce](index.html#1487837956%2FFunctions%2F2137835383)(intent: [TranslateComponent.Intent](-intent/index.html)) |

