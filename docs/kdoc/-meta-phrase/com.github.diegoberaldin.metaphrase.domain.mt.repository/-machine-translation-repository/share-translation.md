---
title: shareTranslation
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository](../index.html)/[MachineTranslationRepository](index.html)/[shareTranslation](share-translation.html)



# shareTranslation



[jvm]\
abstract suspend fun [shareTranslation](share-translation.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))



Share a translation with the machine translation provider i.e. contribute a segment to the remote TM. This implies transferring data to a third party engine, so handle with care.



#### Parameters


jvm

| | |
|---|---|
| provider | Machine translation provider |
| key | API key (optional) |
| sourceMessage | Source message |
| sourceLang | Source language code |
| targetMessage | Target message |
| targetLang | Target language code |




