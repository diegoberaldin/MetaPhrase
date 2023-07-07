---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation](../../index.html)/[GlossaryComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [Clear](-clear/index.html) |
| [Load](-load/index.html) |
| [AddSourceTerm](-add-source-term/index.html) |
| [AddTargetTerm](-add-target-term/index.html) |
| [DeleteTerm](-delete-term/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddSourceTerm](-add-source-term/index.html) | [jvm]<br>data class [AddSourceTerm](-add-source-term/index.html)(val lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [GlossaryComponent.Intent](index.html)<br>Add a source term. |
| [AddTargetTerm](-add-target-term/index.html) | [jvm]<br>data class [AddTargetTerm](-add-target-term/index.html)(val lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val source: [GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)) : [GlossaryComponent.Intent](index.html)<br>Add a target term. |
| [Clear](-clear/index.html) | [jvm]<br>object [Clear](-clear/index.html) : [GlossaryComponent.Intent](index.html)<br>Clear the section content. |
| [DeleteTerm](-delete-term/index.html) | [jvm]<br>data class [DeleteTerm](-delete-term/index.html)(val term: [GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)) : [GlossaryComponent.Intent](index.html)<br>Delete a term. |
| [Load](-load/index.html) | [jvm]<br>data class [Load](-load/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [GlossaryComponent.Intent](index.html)<br>Load the glossary terms for the message with a given key.. |

