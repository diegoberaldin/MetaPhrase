---
title: getTargetMessage
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao](../index.html)/[DefaultMemoryEntryDao](index.html)/[getTargetMessage](get-target-message.html)



# getTargetMessage



[jvm]\
open suspend override fun [getTargetMessage](get-target-message.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html)?



Get the target message for a given TM entry key.



#### Return



[TranslationMemoryEntryModel](../../com.github.diegoberaldin.metaphrase.domain.tm.data/-translation-memory-entry-model/index.html) or null



#### Parameters


jvm

| | |
|---|---|
| lang | target language code |
| key | message key |




