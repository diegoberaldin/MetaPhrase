---
title: DefaultGlossaryTermDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao](../index.html)/[DefaultGlossaryTermDao](index.html)



# DefaultGlossaryTermDao



[jvm]\
class [DefaultGlossaryTermDao](index.html) : [GlossaryTermDao](../-glossary-term-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultGlossaryTermDao](-default-glossary-term-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [areAssociated](are-associated.html) | [jvm]<br>open suspend override fun [areAssociated](are-associated.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determine whether two terms are associated. |
| [associate](associate.html) | [jvm]<br>open suspend override fun [associate](associate.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Create an association between two terms. |
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new glossary term. |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html))<br>Delete a glossary term. |
| [deleteAll](delete-all.html) | [jvm]<br>open suspend override fun [deleteAll](delete-all.html)()<br>Delete all the terms in the glossary. |
| [disassociate](disassociate.html) | [jvm]<br>open suspend override fun [disassociate](disassociate.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Remove the association between two terms. |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get all the terms in the glossary. |
| [getAllAssociated](get-all-associated.html) | [jvm]<br>open suspend override fun [getAllAssociated](get-all-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get all terms associated with a given term no matter the language. |
| [getAssociated](get-associated.html) | [jvm]<br>open suspend override fun [getAssociated](get-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), otherLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get the terms associated with a given term. |
| [getBy](get-by.html) | [jvm]<br>open suspend override fun [getBy](get-by.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?<br>Get a term given its lemma and language. |
| [getById](get-by-id.html) | [jvm]<br>open suspend override fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?<br>Get a tern given its id. |
| [isStillReferenced](is-still-referenced.html) | [jvm]<br>open suspend override fun [isStillReferenced](is-still-referenced.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determine whether a term is still referenced by any other term in the glossary. |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html))<br>Update a glossary term. |

