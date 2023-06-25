---
title: DefaultUserDefinedWordsRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.repo](../index.html)/[DefaultUserDefinedWordsRepository](index.html)



# DefaultUserDefinedWordsRepository



[jvm]\
class [DefaultUserDefinedWordsRepository](index.html)(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html), fileManager: [FileManager](../../com.github.diegoberaldin.metaphrase.core.common.files/-file-manager/index.html)) : [UserDefinedWordsRepository](../-user-defined-words-repository/index.html)



## Constructors


| | |
|---|---|
| [DefaultUserDefinedWordsRepository](-default-user-defined-words-repository.html) | [jvm]<br>constructor(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html), fileManager: [FileManager](../../com.github.diegoberaldin.metaphrase.core.common.files/-file-manager/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [add](add.html) | [jvm]<br>open suspend override fun [add](add.html)(word: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Add a new user-defined word. |
| [clear](clear.html) | [jvm]<br>open suspend override fun [clear](clear.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Clear all the user-defined words for a language. |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Get all user-defined words. |

