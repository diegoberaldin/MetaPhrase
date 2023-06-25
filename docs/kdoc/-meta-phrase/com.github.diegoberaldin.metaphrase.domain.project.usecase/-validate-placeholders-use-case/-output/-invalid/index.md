---
title: Invalid
---
//[MetaPhrase](../../../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../../../index.html)/[ValidatePlaceholdersUseCase](../../index.html)/[Output](../index.html)/[Invalid](index.html)



# Invalid



[jvm]\
data class [Invalid](index.html)(val keys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList()) : [ValidatePlaceholdersUseCase.Output](../index.html)

Case where some errors were detected.



## Constructors


| | |
|---|---|
| [Invalid](-invalid.html) | [jvm]<br>constructor(keys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; = emptyList())<br>Create [Invalid](index.html) |


## Properties


| Name | Summary |
|---|---|
| [keys](keys.html) | [jvm]<br>val [keys](keys.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>list of the keys of the messages where errors were detected |

