---
title: UserDefinedWordsRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.repo](../index.html)/[UserDefinedWordsRepository](index.html)



# UserDefinedWordsRepository

interface [UserDefinedWordsRepository](index.html)

#### Inheritors


| |
|---|
| [DefaultUserDefinedWordsRepository](../-default-user-defined-words-repository/index.html) |


## Functions


| Name | Summary |
|---|---|
| [add](add.html) | [jvm]<br>abstract suspend fun [add](add.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [clear](clear.html) | [jvm]<br>abstract suspend fun [clear](clear.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

