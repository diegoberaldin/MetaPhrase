---
title: getTranslation
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource](../index.html)/[MachineTranslationDataSource](index.html)/[getTranslation](get-translation.html)



# getTranslation



[jvm]\
abstract suspend fun [getTranslation](get-translation.html)(sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Get a suggestion (translation) from the remote provider.



#### Return



a translation for the message



#### Parameters


jvm

| | |
|---|---|
| sourceMessage | Source message |
| sourceLang | Source language code |
| targetLang | Target language code |
| key | API key (optional) |




