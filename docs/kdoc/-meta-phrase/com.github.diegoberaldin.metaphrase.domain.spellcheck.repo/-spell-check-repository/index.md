---
title: SpellCheckRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.repo](../index.html)/[SpellCheckRepository](index.html)



# SpellCheckRepository



[jvm]\
interface [SpellCheckRepository](index.html)

Contract for the spell check repository.



## Functions


| Name | Summary |
|---|---|
| [addUserDefineWord](add-user-define-word.html) | [jvm]<br>abstract suspend fun [addUserDefineWord](add-user-define-word.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Add a user-defined word. |
| [check](check.html) | [jvm]<br>abstract suspend fun [check](check.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt;<br>Check a message for spelling errors. |
| [setLanguage](set-language.html) | [jvm]<br>abstract suspend fun [setLanguage](set-language.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set language for the checks. |

