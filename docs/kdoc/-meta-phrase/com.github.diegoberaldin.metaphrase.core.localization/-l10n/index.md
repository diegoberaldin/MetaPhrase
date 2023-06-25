---
title: L10n
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.localization](../index.html)/[L10n](index.html)



# L10n



[jvm]\
object [L10n](index.html)

Global entry point for the app localization.



## Properties


| Name | Summary |
|---|---|
| [currentLanguage](current-language.html) | [jvm]<br>val [currentLanguage](current-language.html): Flow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Exposes the ISO 693-1 code of the current language as an observable flow. |


## Functions


| Name | Summary |
|---|---|
| [get](get.html) | [jvm]<br>fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get the message associated to a given key. If the key is not present in the current language, a default value (base language) should be returned. If the key does not exist even in the base bundle, the key itself should be returned.<br>[jvm]<br>fun [get](get.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), vararg args: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Get the message associated to a given key with format arguments. |
| [setLanguage](set-language.html) | [jvm]<br>fun [setLanguage](set-language.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set the app language. |

