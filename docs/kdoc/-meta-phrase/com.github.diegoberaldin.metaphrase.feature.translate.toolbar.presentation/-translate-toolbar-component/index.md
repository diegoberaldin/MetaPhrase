---
title: TranslateToolbarComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation](../index.html)/[TranslateToolbarComponent](index.html)



# TranslateToolbarComponent



[jvm]\
interface [TranslateToolbarComponent](index.html)

Translation toolbar component.



## Types


| Name | Summary |
|---|---|
| [Events](-events/index.html) | [jvm]<br>interface [Events](-events/index.html)<br>Events that can be emitted by the component. |


## Properties


| Name | Summary |
|---|---|
| [currentLanguage](current-language.html) | [jvm]<br>abstract val [currentLanguage](current-language.html): StateFlow&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?&gt;<br>Current language selected in the toolbar |
| [events](events.html) | [jvm]<br>abstract val [events](events.html): SharedFlow&lt;[TranslateToolbarComponent.Events](-events/index.html)&gt;<br>Event flow |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Current project ID |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[TranslateToolbarUiState](../-translate-toolbar-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [addUnit](add-unit.html) | [jvm]<br>abstract fun [addUnit](add-unit.html)()<br>Trigger the [Events.AddUnit](-events/-add-unit/index.html) event to be emitted in the [events](events.html) flow. |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)()<br>Trigger the [Events.CopyBase](-events/-copy-base/index.html) event to be emitted in the [events](events.html) flow. |
| [moveToNext](move-to-next.html) | [jvm]<br>abstract fun [moveToNext](move-to-next.html)()<br>Trigger the [Events.MoveToNext](-events/-move-to-next/index.html) event to be emitted in the [events](events.html) flow. |
| [moveToPrevious](move-to-previous.html) | [jvm]<br>abstract fun [moveToPrevious](move-to-previous.html)()<br>Trigger the [Events.MoveToPrevious](-events/-move-to-previous/index.html) event to be emitted in the [events](events.html) flow. |
| [onSearchFired](on-search-fired.html) | [jvm]<br>abstract fun [onSearchFired](on-search-fired.html)()<br>Trigger the [Events.Search](-events/-search/index.html) event to be emitted in the [events](events.html) flow with the last value passed to the [setSearch](set-search.html) method. |
| [removeUnit](remove-unit.html) | [jvm]<br>abstract fun [removeUnit](remove-unit.html)()<br>Trigger the [Events.RemoveUnit](-events/-remove-unit/index.html) event to be emitted in the [events](events.html) flow. |
| [setEditing](set-editing.html) | [jvm]<br>abstract fun [setEditing](set-editing.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Set the editing state. |
| [setLanguage](set-language.html) | [jvm]<br>abstract fun [setLanguage](set-language.html)(value: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Change the current language. |
| [setSearch](set-search.html) | [jvm]<br>abstract fun [setSearch](set-search.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set a search query string |
| [setTypeFilter](set-type-filter.html) | [jvm]<br>abstract fun [setTypeFilter](set-type-filter.html)(value: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html))<br>Change the message filter. |
| [validateUnits](validate-units.html) | [jvm]<br>abstract fun [validateUnits](validate-units.html)()<br>Trigger the [Events.ValidateUnits](-events/-validate-units/index.html) event to be emitted in the [events](events.html) flow. This is intended for placeholder validation. |

