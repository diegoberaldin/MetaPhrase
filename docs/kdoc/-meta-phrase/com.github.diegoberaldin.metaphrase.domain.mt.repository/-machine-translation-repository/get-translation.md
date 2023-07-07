---
title: getTranslation
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository](../index.html)/[MachineTranslationRepository](index.html)/[getTranslation](get-translation.html)



# getTranslation



[jvm]\
abstract suspend fun [getTranslation](get-translation.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Get a suggestion (translation) from machine translation.



#### Return



a suggestion from the MT provider



#### Parameters


jvm

| | |
|---|---|
| provider | Machine translation provider |
| key | API key for the provider (optional) |
| sourceMessage | Message to translate |
| sourceLang | Source language code |
| targetLang | Target language code |




