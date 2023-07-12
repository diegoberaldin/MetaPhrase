---
title: getByIdentifier
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.repository](../index.html)/[MemoryEntryRepository](index.html)/[getByIdentifier](get-by-identifier.html)



# getByIdentifier



[jvm]\
abstract suspend fun [getByIdentifier](get-by-identifier.html)(identifier: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), origin: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?



Get translation memory entries by identifier.



#### Return



[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html) or null



#### Parameters


jvm

| | |
|---|---|
| identifier | Identifier (message key or tuid) |
| origin | Origin of translation memory entries |
| sourceLang | Source language code |
| targetLang | Target language code |



