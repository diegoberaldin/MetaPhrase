---
title: MachineTranslationRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository](../index.html)/[MachineTranslationRepository](index.html)



# MachineTranslationRepository



[jvm]\
interface [MachineTranslationRepository](index.html)

Contract for the machine translation repository.



## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [generateKey](generate-key.html) | [jvm]<br>abstract suspend fun [generateKey](generate-key.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generate an API key for a machine translation provider. This is supported only by some providers, e.g. [MachineTranslationProvider.MY_MEMORY](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/-m-y_-m-e-m-o-r-y/index.html) |
| [getTranslation](get-translation.html) | [jvm]<br>abstract suspend fun [getTranslation](get-translation.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get a suggestion (translation) from machine translation. |
| [importTm](import-tm.html) | [jvm]<br>abstract suspend fun [importTm](import-tm.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, private: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Contribute a whole TMX file to the remote service. This implies transferring data to a third party engine, so handle with care. |
| [shareTranslation](share-translation.html) | [jvm]<br>abstract suspend fun [shareTranslation](share-translation.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Share a translation with the machine translation provider i.e. contribute a segment to the remote TM. This implies transferring data to a third party engine, so handle with care. |

