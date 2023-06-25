---
title: import
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource](../index.html)/[MachineTranslationDataSource](index.html)/[import](import.html)



# import



[jvm]\
abstract suspend fun [import](import.html)(file: [File](https://docs.oracle.com/javase/8/docs/api/java/io/File.html), key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, private: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, subject: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null)



Import a TMX file to the remote provider.



#### Parameters


jvm

| | |
|---|---|
| file | File of the TM |
| key | API key (optional) |
| private | if set to `true` the segments will not be visible to other users of the remote provider (implies the `key` parameter is passed) |
| name | Name of the TM (optional) |
| subject | Subject of the TM (optional) |




