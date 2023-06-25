---
title: DefaultLanguageDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.language.persistence.dao](../index.html)/[DefaultLanguageDao](index.html)



# DefaultLanguageDao



[jvm]\
class [DefaultLanguageDao](index.html) : [LanguageDao](../-language-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultLanguageDao](-default-language-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a language within a given project. |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Delete a language. |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt;<br>Get all the languages for a project. |
| [getBase](get-base.html) | [jvm]<br>open suspend override fun [getBase](get-base.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get the source language for a given project. |
| [getByCode](get-by-code.html) | [jvm]<br>open suspend override fun [getByCode](get-by-code.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get a language by code within a given project. |
| [getById](get-by-id.html) | [jvm]<br>open suspend override fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?<br>Get a language given its ID. |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html))<br>Update a language. |

