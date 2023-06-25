---
title: getByIdentifier
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao](../index.html)/[MemoryEntryDao](index.html)/[getByIdentifier](get-by-identifier.html)



# getByIdentifier



[jvm]\
abstract suspend fun [getByIdentifier](get-by-identifier.html)(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?



Get a TM entry by identifier.



#### Return



[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html) or null



#### Parameters


jvm

| | |
|---|---|
| identifier | Identifier (key or tuid) |
| origin | Origin |
| sourceLang | Source language code |
| targetLang | Target language code |




