---
title: generateKey
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository](../index.html)/[MachineTranslationRepository](index.html)/[generateKey](generate-key.html)



# generateKey



[jvm]\
abstract suspend fun [generateKey](generate-key.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Generate an API key for a machine translation provider. This is supported only by some providers, e.g. [MachineTranslationProvider.MY_MEMORY](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/-m-y_-m-e-m-o-r-y/index.html)



#### Return



a valid API key for queries



#### Parameters


jvm

| | |
|---|---|
| provider | Provider |
| username | Username of the account |
| password | Password of the account |




