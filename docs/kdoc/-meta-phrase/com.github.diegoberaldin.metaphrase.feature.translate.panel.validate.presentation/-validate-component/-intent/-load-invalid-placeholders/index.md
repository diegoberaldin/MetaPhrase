---
title: LoadInvalidPlaceholders
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../../../index.html)/[ValidateComponent](../../index.html)/[Intent](../index.html)/[LoadInvalidPlaceholders](index.html)



# LoadInvalidPlaceholders

data class [LoadInvalidPlaceholders](index.html)(val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val invalidKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [ValidateComponent.Intent](../index.html)

Load a list of invalid placeholder references.



#### Parameters


jvm

| | |
|---|---|
| projectId | Project ID |
| languageId | Language ID |
| invalidKeys | Invalid message keys |



## Constructors


| | |
|---|---|
| [LoadInvalidPlaceholders](-load-invalid-placeholders.html) | [jvm]<br>constructor(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), invalidKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |


## Properties


| Name | Summary |
|---|---|
| [invalidKeys](invalid-keys.html) | [jvm]<br>val [invalidKeys](invalid-keys.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [languageId](language-id.html) | [jvm]<br>val [languageId](language-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [projectId](project-id.html) | [jvm]<br>val [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

