---
title: InvalidPlaceholderReference
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.data](../index.html)/[InvalidPlaceholderReference](index.html)



# InvalidPlaceholderReference



[jvm]\
data class [InvalidPlaceholderReference](index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val extraPlaceholders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, val missingPlaceholders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Invalid placeholder reference.



## Constructors


| | |
|---|---|
| [InvalidPlaceholderReference](-invalid-placeholder-reference.html) | [jvm]<br>constructor(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), extraPlaceholders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, missingPlaceholders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)<br>Create [InvalidPlaceholderReference](index.html) |


## Properties


| Name | Summary |
|---|---|
| [extraPlaceholders](extra-placeholders.html) | [jvm]<br>val [extraPlaceholders](extra-placeholders.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>placeholders in the target message that are not found in the source message |
| [key](key.html) | [jvm]<br>val [key](key.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>message key |
| [missingPlaceholders](missing-placeholders.html) | [jvm]<br>val [missingPlaceholders](missing-placeholders.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>placeholders in the source message that are not found in the target message |
