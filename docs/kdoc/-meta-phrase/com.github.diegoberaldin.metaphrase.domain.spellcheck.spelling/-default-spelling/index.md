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
| [isInitialized](is-initialized.html) | [jvm]<br>open override val [isInitialized](is-initialized.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Functions


| Name | Summary |
|---|---|
| [addUserDefinedWord](add-user-defined-word.html) | [jvm]<br>open suspend override fun [addUserDefinedWord](add-user-defined-word.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [check](check.html) | [jvm]<br>open override fun [check](check.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [checkSentence](check-sentence.html) | [jvm]<br>open override fun [checkSentence](check-sentence.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SpellCheckCorrection](../../com.github.diegoberaldin.metaphrase.domain.spellcheck/-spell-check-correction/index.html)&gt; |
| [getLemmata](get-lemmata.html) | [jvm]<br>open override fun [getLemmata](get-lemmata.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [setLanguage](set-language.html) | [jvm]<br>open suspend override fun [setLanguage](set-language.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

