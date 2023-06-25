---
title: getTranslation
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.repository](../index.html)/[MemoryEntryRepository](index.html)/[getTranslation](get-translation.html)



# getTranslation



[jvm]\
abstract suspend fun [getTranslation](get-translation.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?



Get an entry with a given target message for a source message.



#### Return



[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html) or null



#### Parameters


jvm

| | |
|---|---|
| lang | target language code |
| key | message key |




