---
title: TemporaryKeyStore
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.keystore](../index.html)/[TemporaryKeyStore](index.html)



# TemporaryKeyStore



[jvm]\
interface [TemporaryKeyStore](index.html)

Secondary storage in the form of a key store (persistence across application restarts).



## Functions


| Name | Summary |
|---|---|
| [get](get.html) | [jvm]<br>abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Retrieve a boolean value from the key store given its key.<br>[jvm]<br>abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Retrieve a floating point (double precision) value from the key store given its key.<br>[jvm]<br>abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)<br>Retrieve a floating point value from the key store given its key.<br>[jvm]<br>abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Retrieve an integer value from the key store given its key.<br>[jvm]<br>abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Retrieve a string value from the key store given its key. |
| [save](save.html) | [jvm]<br>abstract suspend fun [save](save.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Save a boolean value in the keystore under a given key.<br>[jvm]<br>abstract suspend fun [save](save.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))<br>Save a floating point (double precision) value in the keystore under a given key.<br>[jvm]<br>abstract suspend fun [save](save.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html))<br>Save a floating point value in the keystore under a given key.<br>[jvm]<br>abstract suspend fun [save](save.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Save an integer value in the keystore under a given key.<br>[jvm]<br>abstract suspend fun [save](save.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Save a string value in the keystore under a given key. |

