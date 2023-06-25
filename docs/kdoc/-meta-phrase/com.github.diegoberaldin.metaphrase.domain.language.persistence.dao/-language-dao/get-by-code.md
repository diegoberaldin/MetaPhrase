---
title: getByCode
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.persistence.dao](../index.html)/[LanguageDao](index.html)/[getByCode](get-by-code.html)



# getByCode



[jvm]\
abstract suspend fun [getByCode](get-by-code.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?



Get a language by code within a given project.



#### Return



[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html) or null if no such language exists



#### Parameters


jvm

| | |
|---|---|
| code | Language code (two letters ISO 693-1 code) |
| projectId | Project ID |




