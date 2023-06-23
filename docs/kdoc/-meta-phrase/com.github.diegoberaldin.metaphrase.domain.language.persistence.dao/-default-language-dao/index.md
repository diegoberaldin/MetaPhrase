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
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)&gt; |
| [getBase](get-base.html) | [jvm]<br>open suspend override fun [getBase](get-base.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? |
| [getByCode](get-by-code.html) | [jvm]<br>open suspend override fun [getByCode](get-by-code.html)(code: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? |
| [getById](get-by-id.html) | [jvm]<br>open suspend override fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)? |
| [toModel](to-model.html) | [jvm]<br>open override fun ResultRow.[toModel](to-model.html)(): [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html) |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

