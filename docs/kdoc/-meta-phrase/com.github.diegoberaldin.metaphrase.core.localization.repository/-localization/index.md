---
title: Localization
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.localization.repository](../index.html)/[Localization](index.html)



# Localization



[jvm]\
interface [Localization](index.html)

Defines the contract for the localization repository.



## Functions


| Name | Summary |
|---|---|
| [get](get.html) | [jvm]<br>abstract fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get a message translation for a given key. |
| [getLanguage](get-language.html) | [jvm]<br>abstract fun [getLanguage](get-language.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get the current language. |
| [setLanguage](set-language.html) | [jvm]<br>abstract fun [setLanguage](set-language.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the app language globally. |

