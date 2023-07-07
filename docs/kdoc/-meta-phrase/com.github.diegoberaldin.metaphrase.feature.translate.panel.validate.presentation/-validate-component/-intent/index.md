---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../../index.html)/[ValidateComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [LoadInvalidPlaceholders](-load-invalid-placeholders/index.html) |
| [LoadSpellingMistakes](-load-spelling-mistakes/index.html) |
| [Clear](-clear/index.html) |
| [SelectItem](-select-item/index.html) |


## Types


| Name | Summary |
|---|---|
| [Clear](-clear/index.html) | [jvm]<br>object [Clear](-clear/index.html) : [ValidateComponent.Intent](index.html)<br>Clear the panel content. |
| [LoadInvalidPlaceholders](-load-invalid-placeholders/index.html) | [jvm]<br>data class [LoadInvalidPlaceholders](-load-invalid-placeholders/index.html)(val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val invalidKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [ValidateComponent.Intent](index.html)<br>Load a list of invalid placeholder references. |
| [LoadSpellingMistakes](-load-spelling-mistakes/index.html) | [jvm]<br>data class [LoadSpellingMistakes](-load-spelling-mistakes/index.html)(val errors: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;) : [ValidateComponent.Intent](index.html)<br>Load a list of spelling mistake references. |
| [SelectItem](-select-item/index.html) | [jvm]<br>data class [SelectItem](-select-item/index.html)(val value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [ValidateComponent.Intent](index.html)<br>Select an reference, triggering [Effect.Selection](../-effect/-selection/index.html). |

