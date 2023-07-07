---
title: Effect
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../../index.html)/[MachineTranslationComponent](../index.html)/[Effect](index.html)



# Effect

interface [Effect](index.html)

Effects.



#### Inheritors


| |
|---|
| [CopySource](-copy-source/index.html) |
| [CopyTarget](-copy-target/index.html) |
| [Share](-share/index.html) |


## Types


| Name | Summary |
|---|---|
| [CopySource](-copy-source/index.html) | [jvm]<br>data class [CopySource](-copy-source/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MachineTranslationComponent.Effect](index.html)<br>Events triggered when the content of the suggestion field from MT needs to be copied into the translation editor. |
| [CopyTarget](-copy-target/index.html) | [jvm]<br>object [CopyTarget](-copy-target/index.html) : [MachineTranslationComponent.Effect](index.html)<br>Events triggered when the content of the translation editor needs to be copied to the suggestion field. |
| [Share](-share/index.html) | [jvm]<br>data class [Share](-share/index.html)(val successful: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [MachineTranslationComponent.Effect](index.html)<br>Events triggered after a message share with the MT provider. |

