---
title: FlagsRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.repository](../index.html)/[FlagsRepository](index.html)



# FlagsRepository



[jvm]\
interface [FlagsRepository](index.html)

Contract for the flag repository.



## Functions


| Name | Summary |
|---|---|
| [getFlag](get-flag.html) | [jvm]<br>abstract fun [getFlag](get-flag.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get a Unicode representation of the flag of the language having a given code. This returns a value only for the supported languages (see [LanguageRepository.getDefaultLanguages](../-language-repository/get-default-languages.html). |

