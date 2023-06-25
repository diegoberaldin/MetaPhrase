---
title: get
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.localization](../index.html)/[L10n](index.html)/[get](get.html)



# get



[jvm]\
fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Get the message associated to a given key. If the key is not present in the current language, a default value (base language) should be returned. If the key does not exist even in the base bundle, the key itself should be returned.



#### Return



the localized string to show in the app UI



#### Parameters


jvm

| | |
|---|---|
| key | Message key |





[jvm]\
fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)



Get the message associated to a given key with format arguments.



#### Return



the localized string to show in the app UI



#### Parameters


jvm

| | |
|---|---|
| key | Message key |
| args | Format arguments |




