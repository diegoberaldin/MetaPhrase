---
title: get
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.common.keystore](../index.html)/[TemporaryKeyStore](index.html)/[get](get.html)



# get



[jvm]\
abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Retrieve a boolean value from the key store given its key.



#### Return



value saved in the keystore or the default one



#### Parameters


jvm

| | |
|---|---|
| key | Key |
| default | Default value |





[jvm]\
abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Retrieve a string value from the key store given its key.



#### Return



value saved in the keystore or the default one



#### Parameters


jvm

| | |
|---|---|
| key | Key |
| default | Default value |





[jvm]\
abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)



Retrieve an integer value from the key store given its key.



#### Return



value saved in the keystore or the default one



#### Parameters


jvm

| | |
|---|---|
| key | Key |
| default | Default value |





[jvm]\
abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)



Retrieve a floating point value from the key store given its key.



#### Return



value saved in the keystore or the default one



#### Parameters


jvm

| | |
|---|---|
| key | Key |
| default | Default value |





[jvm]\
abstract suspend fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), default: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)



Retrieve a floating point (double precision) value from the key store given its key.



#### Return



value saved in the keystore or the default one



#### Parameters


jvm

| | |
|---|---|
| key | Key |
| default | Default value |




