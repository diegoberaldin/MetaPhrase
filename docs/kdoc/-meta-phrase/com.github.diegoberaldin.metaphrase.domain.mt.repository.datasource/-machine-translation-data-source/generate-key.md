---
title: generateKey
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource](../index.html)/[MachineTranslationDataSource](index.html)/[generateKey](generate-key.html)



# generateKey



[jvm]\
abstract suspend fun [generateKey](generate-key.html)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Generate an API key. This may not be supported by all providers. It is up to the implementation whether to return an empty string or throw an exception if the operation is not supported.



#### Return



an API key



#### Parameters


jvm

| | |
|---|---|
| username | Username of the account |
| password | Password of the account |




