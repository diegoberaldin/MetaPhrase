---
title: Load
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../../../index.html)/[MachineTranslationComponent](../../index.html)/[Intent](../index.html)/[Load](index.html)



# Load

data class [Load](index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MachineTranslationComponent.Intent](../index.html)

Load the data for the message with a given key. No suggestion is retrieved until the retrieve method is called. This is intended to reduce the request number and not exceed the service quota.



#### Parameters


jvm

| | |
|---|---|
| key | message key |
| projectId | Project ID |
| languageId | Language ID |



## Constructors


| | |
|---|---|
| [Load](-load.html) | [jvm]<br>constructor(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [languageId](language-id.html) | [jvm]<br>val [languageId](language-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [projectId](project-id.html) | [jvm]<br>val [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

