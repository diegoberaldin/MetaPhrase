---
title: getFlag
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.repository](../index.html)/[FlagsRepository](index.html)/[getFlag](get-flag.html)



# getFlag



[jvm]\
abstract fun [getFlag](get-flag.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Get a Unicode representation of the flag of the language having a given code. This returns a value only for the supported languages (see [LanguageRepository.getDefaultLanguages](../-language-repository/get-default-languages.html).



#### Return



an emoji corresponding to the country flag



#### Parameters


jvm

| | |
|---|---|
| code | ISO 693-1 language code |




