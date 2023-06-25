---
title: SpellCheckCorrection
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.data](../index.html)/[SpellCheckCorrection](index.html)



# SpellCheckCorrection



[jvm]\
data class [SpellCheckCorrection](index.html)(val indices: [IntRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html) = IntRange.EMPTY, val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val suggestions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())

Spelling check correction.



## Constructors


| | |
|---|---|
| [SpellCheckCorrection](-spell-check-correction.html) | [jvm]<br>constructor(indices: [IntRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html) = IntRange.EMPTY, value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, suggestions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())<br>Create [SpellCheckCorrection](index.html) |


## Properties


| Name | Summary |
|---|---|
| [indices](indices.html) | [jvm]<br>val [indices](indices.html): [IntRange](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html)<br>range of the error in the source message |
| [suggestions](suggestions.html) | [jvm]<br>val [suggestions](suggestions.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>spelling suggestions |
| [value](value.html) | [jvm]<br>val [value](value.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>misspelled word |

