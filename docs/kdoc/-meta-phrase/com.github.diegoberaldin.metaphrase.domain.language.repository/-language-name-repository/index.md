---
title: LanguageNameRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.repository](../index.html)/[LanguageNameRepository](index.html)



# LanguageNameRepository



[jvm]\
interface [LanguageNameRepository](index.html)

Contract for the language name repository.



## Functions


| Name | Summary |
|---|---|
| [getName](get-name.html) | [jvm]<br>abstract fun [getName](get-name.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get a user-friendly name for the language based on its ISO code (ISO 693-1 standard). This returns a value only for the supported languages (see [LanguageRepository.getDefaultLanguages](../-language-repository/get-default-languages.html). |

