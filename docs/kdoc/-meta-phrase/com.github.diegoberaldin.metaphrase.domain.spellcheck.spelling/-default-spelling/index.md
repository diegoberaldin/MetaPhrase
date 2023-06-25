---
title: DefaultSpelling
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling](../index.html)/[DefaultSpelling](index.html)



# DefaultSpelling



[jvm]\
class [DefaultSpelling](index.html)(userDefinedWordsRepository: [UserDefinedWordsRepository](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.repo/-user-defined-words-repository/index.html)) : [Spelling](../-spelling/index.html)



## Constructors


| | |
|---|---|
| [DefaultSpelling](-default-spelling.html) | [jvm]<br>constructor(userDefinedWordsRepository: [UserDefinedWordsRepository](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.repo/-user-defined-words-repository/index.html)) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Properties


| Name | Summary |
|---|---|
| [isInitialized](is-initialized.html) | [jvm]<br>open override val [isInitialized](is-initialized.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Flag indicating whether the spell checker is initialized |


## Functions


| Name | Summary |
|---|---|
| [addUserDefinedWord](add-user-defined-word.html) | [jvm]<br>open suspend override fun [addUserDefinedWord](add-user-defined-word.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Add a user defined word. |
| [check](check.html) | [jvm]<br>open override fun [check](check.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Check a word for spelling mistakes. |
| [checkSentence](check-sentence.html) | [jvm]<br>open override fun [checkSentence](check-sentence.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.data/-spell-check-correction/index.html)&gt;<br>Check a whole sentence for spelling mistakes. |
| [getLemmata](get-lemmata.html) | [jvm]<br>open override fun [getLemmata](get-lemmata.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Analyze a message to get a list of word stems. |
| [setLanguage](set-language.html) | [jvm]<br>open suspend override fun [setLanguage](set-language.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set language for spell check. |

