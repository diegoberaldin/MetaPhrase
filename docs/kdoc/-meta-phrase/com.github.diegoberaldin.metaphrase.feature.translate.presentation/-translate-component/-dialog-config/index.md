---
title: DialogConfig
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../../index.html)/[TranslateComponent](../index.html)/[DialogConfig](index.html)



# DialogConfig

interface [DialogConfig](index.html) : Parcelable

Available dialog configurations for the [dialog](../dialog.html) slot.



#### Inheritors


| |
|---|
| [None](-none/index.html) |
| [NewSegment](-new-segment/index.html) |
| [NewGlossaryTerm](-new-glossary-term/index.html) |


## Types


| Name | Summary |
|---|---|
| [NewGlossaryTerm](-new-glossary-term/index.html) | [jvm]<br>data class [NewGlossaryTerm](-new-glossary-term/index.html)(val target: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [TranslateComponent.DialogConfig](index.html)<br>New glossary term dialog |
| [NewSegment](-new-segment/index.html) | [jvm]<br>object [NewSegment](-new-segment/index.html) : [TranslateComponent.DialogConfig](index.html)<br>New segment dialog |
| [None](-none/index.html) | [jvm]<br>object [None](-none/index.html) : [TranslateComponent.DialogConfig](index.html)<br>No dialog (close any current dialog) |

