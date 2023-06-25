---
title: load
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../index.html)/[MachineTranslationComponent](index.html)/[load](load.html)



# load



[jvm]\
abstract fun [load](load.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))



Load the data for the message with a given key. No suggestion is retrieved until the [retrieve](retrieve.html) method is called. This is intended to reduce the request number and not exceed the service quota.



#### Parameters


jvm

| | |
|---|---|
| key | message key |
| projectId | Project ID |
| languageId | Language ID |




