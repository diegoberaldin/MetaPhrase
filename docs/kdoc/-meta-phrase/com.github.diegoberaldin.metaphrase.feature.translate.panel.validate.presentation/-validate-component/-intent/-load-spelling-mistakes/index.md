---
title: LoadSpellingMistakes
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../../../index.html)/[ValidateComponent](../../index.html)/[Intent](../index.html)/[LoadSpellingMistakes](index.html)



# LoadSpellingMistakes

data class [LoadSpellingMistakes](index.html)(val errors: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;) : [ValidateComponent.Intent](../index.html)

Load a list of spelling mistake references.



#### Parameters


jvm

| | |
|---|---|
| errors | list of spelling errors (message key to list of incorrect words) |



## Constructors


| | |
|---|---|
| [LoadSpellingMistakes](-load-spelling-mistakes.html) | [jvm]<br>constructor(errors: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;) |


## Properties


| Name | Summary |
|---|---|
| [errors](errors.html) | [jvm]<br>val [errors](errors.html): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |

