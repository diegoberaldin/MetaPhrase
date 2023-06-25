---
title: UserDefinedWordsRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.repo](../index.html)/[UserDefinedWordsRepository](index.html)



# UserDefinedWordsRepository

interface [UserDefinedWordsRepository](index.html)

Contract for the user-defined words repository.



#### Inheritors


| |
|---|
| [DefaultUserDefinedWordsRepository](../-default-user-defined-words-repository/index.html) |


## Functions


| Name | Summary |
|---|---|
| [add](add.html) | [jvm]<br>abstract suspend fun [add](add.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Add a new user-defined word. |
| [clear](clear.html) | [jvm]<br>abstract suspend fun [clear](clear.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Clear all the user-defined words for a language. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Get all user-defined words. |

