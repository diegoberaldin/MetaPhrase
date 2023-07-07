---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](../../index.html)/[NewGlossaryTermComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [SetSourceTerm](-set-source-term/index.html) |
| [SetTargetTerm](-set-target-term/index.html) |
| [Submit](-submit/index.html) |


## Types


| Name | Summary |
|---|---|
| [SetSourceTerm](-set-source-term/index.html) | [jvm]<br>data class [SetSourceTerm](-set-source-term/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [NewGlossaryTermComponent.Intent](index.html)<br>Set source term. |
| [SetTargetTerm](-set-target-term/index.html) | [jvm]<br>data class [SetTargetTerm](-set-target-term/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [NewGlossaryTermComponent.Intent](index.html)<br>Set target term. |
| [Submit](-submit/index.html) | [jvm]<br>object [Submit](-submit/index.html) : [NewGlossaryTermComponent.Intent](index.html)<br>Confirm the inserted term pair. |

