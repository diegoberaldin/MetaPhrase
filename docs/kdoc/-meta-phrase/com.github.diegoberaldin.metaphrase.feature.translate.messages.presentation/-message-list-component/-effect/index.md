---
title: Effect
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation](../../index.html)/[MessageListComponent](../index.html)/[Effect](index.html)



# Effect

interface [Effect](index.html)

Effects.



#### Inheritors


| |
|---|
| [Selection](-selection/index.html) |
| [AddToGlossary](-add-to-glossary/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddToGlossary](-add-to-glossary/index.html) | [jvm]<br>data class [AddToGlossary](-add-to-glossary/index.html)(val lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MessageListComponent.Effect](index.html)<br>Events emitted when new terms should be added to the glossary. |
| [Selection](-selection/index.html) | [jvm]<br>data class [Selection](-selection/index.html)(val index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MessageListComponent.Effect](index.html)<br>Event triggered with the index of the message being edited (needed to scroll the list programmatically) |

