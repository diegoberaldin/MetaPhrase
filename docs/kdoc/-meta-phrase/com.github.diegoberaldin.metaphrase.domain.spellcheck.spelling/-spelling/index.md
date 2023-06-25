---
title: Spelling
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling](../index.html)/[Spelling](index.html)



# Spelling

interface [Spelling](index.html)

Contract for the spelling checker.



#### Inheritors


| |
|---|
| [DefaultSpelling](../-default-spelling/index.html) |


## Properties


| Name | Summary |
|---|---|
| [isInitialized](is-initialized.html) | [jvm]<br>abstract val [isInitialized](is-initialized.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Flag indicating whether the spell checker is initialized |


## Functions


| Name | Summary |
|---|---|
| [addUserDefinedWord](add-user-defined-word.html) | [jvm]<br>abstract suspend fun [addUserDefinedWord](add-user-defined-word.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Add a user defined word. |
| [check](check.html) | [jvm]<br>abstract fun [check](check.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Check a word for spelling mistakes. |
| [checkSentence](check-sentence.html) | [jvm]<br>abstract fun [checkSentence](check-sentence.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt;<br>Check a whole sentence for spelling mistakes. |
| [getLemmata](get-lemmata.html) | [jvm]<br>abstract fun [getLemmata](get-lemmata.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Analyze a message to get a list of word stems. |
| [setLanguage](set-language.html) | [jvm]<br>abstract suspend fun [setLanguage](set-language.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set language for spell check. |

