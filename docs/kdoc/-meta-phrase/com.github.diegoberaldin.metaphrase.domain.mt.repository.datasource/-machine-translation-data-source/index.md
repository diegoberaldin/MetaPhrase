---
title: MachineTranslationDataSource
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource](../index.html)/[MachineTranslationDataSource](index.html)



# MachineTranslationDataSource



[jvm]\
interface [MachineTranslationDataSource](index.html)

Contract for any machine translation connector with a remote provider. This interface is just for documentation, since concrete implementations are injected in com.github.diegoberaldin.metaphrase.domain.mt.repository.DefaultMachineTranslationRepository.



## Functions


| Name | Summary |
|---|---|
| [contributeTranslation](contribute-translation.html) | [jvm]<br>abstract suspend fun [contributeTranslation](contribute-translation.html)(sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Share a translation to the remote provider. |
| [generateKey](generate-key.html) | [jvm]<br>abstract suspend fun [generateKey](generate-key.html)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generate an API key. This may not be supported by all providers. It is up to the implementation whether to return an empty string or throw an exception if the operation is not supported. |
| [getTranslation](get-translation.html) | [jvm]<br>abstract suspend fun [getTranslation](get-translation.html)(sourceMessage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), sourceLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), targetLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get a suggestion (translation) from the remote provider. |
| [import](import.html) | [jvm]<br>abstract suspend fun [import](import.html)(file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, private: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)<br>Import a TMX file to the remote provider. |

