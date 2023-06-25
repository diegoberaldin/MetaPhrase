---
title: LanguageRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.repository](../index.html)/[LanguageRepository](index.html)



# LanguageRepository



[jvm]\
interface [LanguageRepository](index.html)

Contract for the language repository.



## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new language within a given project. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Delete a language. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>Get all the languages within a given project. |
| [getBase](get-base.html) | [jvm]<br>abstract suspend fun [getBase](get-base.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get the source (base) language of a project. |
| [getByCode](get-by-code.html) | [jvm]<br>abstract suspend fun [getByCode](get-by-code.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get a language by its code. |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get a language by its id. |
| [getDefaultLanguages](get-default-languages.html) | [jvm]<br>abstract fun [getDefaultLanguages](get-default-languages.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>Get the default languages for project creation. |
| [observeAll](observe-all.html) | [jvm]<br>abstract fun [observeAll](observe-all.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;&gt;<br>Observe all the languages within a project. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Update a language. |

