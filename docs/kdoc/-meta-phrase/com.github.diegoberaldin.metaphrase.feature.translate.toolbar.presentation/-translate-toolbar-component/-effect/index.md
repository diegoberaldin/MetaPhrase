---
title: Effect
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation](../../index.html)/[TranslateToolbarComponent](../index.html)/[Effect](index.html)



# Effect

interface [Effect](index.html)

Events that can be emitted by the component.



#### Inheritors


| |
|---|
| [MoveToPrevious](-move-to-previous/index.html) |
| [MoveToNext](-move-to-next/index.html) |
| [Search](-search/index.html) |
| [AddUnit](-add-unit/index.html) |
| [RemoveUnit](-remove-unit/index.html) |
| [ValidateUnits](-validate-units/index.html) |
| [CopyBase](-copy-base/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddUnit](-add-unit/index.html) | [jvm]<br>object [AddUnit](-add-unit/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Add new segment |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Copy base (source) text to translation field |
| [MoveToNext](-move-to-next/index.html) | [jvm]<br>object [MoveToNext](-move-to-next/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Move to next segment |
| [MoveToPrevious](-move-to-previous/index.html) | [jvm]<br>object [MoveToPrevious](-move-to-previous/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Move to previous segment |
| [RemoveUnit](-remove-unit/index.html) | [jvm]<br>object [RemoveUnit](-remove-unit/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Delete current segment |
| [Search](-search/index.html) | [jvm]<br>data class [Search](-search/index.html)(val text: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [TranslateToolbarComponent.Effect](index.html)<br>Search in message list. |
| [ValidateUnits](-validate-units/index.html) | [jvm]<br>object [ValidateUnits](-validate-units/index.html) : [TranslateToolbarComponent.Effect](index.html)<br>Start validation (placeholder) |

