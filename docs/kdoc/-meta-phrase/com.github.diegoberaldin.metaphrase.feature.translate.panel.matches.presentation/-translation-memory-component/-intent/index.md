---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation](../../index.html)/[TranslationMemoryComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [Clear](-clear/index.html) |
| [Load](-load/index.html) |
| [CopyTranslation](-copy-translation/index.html) |


## Types


| Name | Summary |
|---|---|
| [Clear](-clear/index.html) | [jvm]<br>object [Clear](-clear/index.html) : [TranslationMemoryComponent.Intent](index.html)<br>Clear the content of the panel. |
| [CopyTranslation](-copy-translation/index.html) | [jvm]<br>data class [CopyTranslation](-copy-translation/index.html)(val index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [TranslationMemoryComponent.Intent](index.html)<br>Copy the match with a given index into the translation field. |
| [Load](-load/index.html) | [jvm]<br>data class [Load](-load/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [TranslationMemoryComponent.Intent](index.html)<br>Load the TM matches for the message with a given key. |

