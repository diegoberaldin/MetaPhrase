---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation](../../index.html)/[TranslateToolbarComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

#### Inheritors


| |
|---|
| [SetLanguage](-set-language/index.html) |
| [SetTypeFilter](-set-type-filter/index.html) |
| [OnSearchFired](-on-search-fired/index.html) |
| [SetSearch](-set-search/index.html) |
| [CopyBase](-copy-base/index.html) |
| [MoveToPrevious](-move-to-previous/index.html) |
| [MoveToNext](-move-to-next/index.html) |
| [AddUnit](-add-unit/index.html) |
| [RemoveUnit](-remove-unit/index.html) |
| [ValidateUnits](-validate-units/index.html) |
| [SetEditing](-set-editing/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddUnit](-add-unit/index.html) | [jvm]<br>object [AddUnit](-add-unit/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.AddUnit](../-effect/-add-unit/index.html) event to be emitted in the effects flow. |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.CopyBase](../-effect/-copy-base/index.html) event to be emitted in the effects flow. |
| [MoveToNext](-move-to-next/index.html) | [jvm]<br>object [MoveToNext](-move-to-next/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.MoveToNext](../-effect/-move-to-next/index.html) event to be emitted in the effects flow. |
| [MoveToPrevious](-move-to-previous/index.html) | [jvm]<br>object [MoveToPrevious](-move-to-previous/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.MoveToPrevious](../-effect/-move-to-previous/index.html) event to be emitted in the effects flow. |
| [OnSearchFired](-on-search-fired/index.html) | [jvm]<br>object [OnSearchFired](-on-search-fired/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.Search](../-effect/-search/index.html) event to be emitted in the effects flow with the last value passed along in the the [SetSearch](-set-search/index.html) intent. |
| [RemoveUnit](-remove-unit/index.html) | [jvm]<br>object [RemoveUnit](-remove-unit/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.RemoveUnit](../-effect/-remove-unit/index.html) event to be emitted in the effects flow. |
| [SetEditing](-set-editing/index.html) | [jvm]<br>data class [SetEditing](-set-editing/index.html)(val value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [TranslateToolbarComponent.Intent](index.html)<br>Set the editing state. |
| [SetLanguage](-set-language/index.html) | [jvm]<br>data class [SetLanguage](-set-language/index.html)(val value: [LanguageModel](../../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)) : [TranslateToolbarComponent.Intent](index.html)<br>Change the current language. |
| [SetSearch](-set-search/index.html) | [jvm]<br>data class [SetSearch](-set-search/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [TranslateToolbarComponent.Intent](index.html)<br>Set a search query string |
| [SetTypeFilter](-set-type-filter/index.html) | [jvm]<br>data class [SetTypeFilter](-set-type-filter/index.html)(val value: [TranslationUnitTypeFilter](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html)) : [TranslateToolbarComponent.Intent](index.html)<br>Change the message filter. |
| [ValidateUnits](-validate-units/index.html) | [jvm]<br>object [ValidateUnits](-validate-units/index.html) : [TranslateToolbarComponent.Intent](index.html)<br>Trigger the [Effect.ValidateUnits](../-effect/-validate-units/index.html) event to be emitted in the effects flow. This is intended for placeholder validation. |

