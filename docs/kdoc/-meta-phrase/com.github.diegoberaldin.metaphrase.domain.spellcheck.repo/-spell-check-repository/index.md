---
title: SpellCheckRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.repo](../index.html)/[SpellCheckRepository](index.html)



# SpellCheckRepository



[jvm]\
interface [SpellCheckRepository](index.html)



## Functions


| Name | Summary |
|---|---|
| [addUserDefineWord](add-user-define-word.html) | [jvm]<br>abstract suspend fun [addUserDefineWord](add-user-define-word.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [check](check.html) | [jvm]<br>abstract suspend fun [check](check.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck/-spell-check-correction/index.html)&gt; |
| [setLanguage](set-language.html) | [jvm]<br>abstract suspend fun [setLanguage](set-language.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

