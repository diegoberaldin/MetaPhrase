---
title: importTm
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository](../index.html)/[MachineTranslationRepository](index.html)/[importTm](import-tm.html)



# importTm



[jvm]\
abstract suspend fun [importTm](import-tm.html)(provider: [MachineTranslationProvider](../../com.github.diegoberaldin.metaphrase.domain.mt.repository.data/-machine-translation-provider/index.html) = MachineTranslationProvider.MY_MEMORY, file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, private: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)



Contribute a whole TMX file to the remote service. This implies transferring data to a third party engine, so handle with care.



#### Parameters


jvm

| | |
|---|---|
| provider | Provider |
| file | File of the TM to share |
| key | API key (optional) |
| private | if set to `true` the TM will not be shared with other users of the service (only if API key is specified) |
| name | Name of the TM |
| subject | Subject of the TM |




