---
title: GlossaryTermRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.repository](../index.html)/[GlossaryTermRepository](index.html)



# GlossaryTermRepository



[jvm]\
interface [GlossaryTermRepository](index.html)

Contract for the glossary term repository.



## Functions


| Name | Summary |
|---|---|
| [areAssociated](are-associated.html) | [jvm]<br>abstract suspend fun [areAssociated](are-associated.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determine whether two glossary terms are associated. |
| [associate](associate.html) | [jvm]<br>abstract suspend fun [associate](associate.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Create an association between two terms. |
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new glossary term. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html))<br>Delete a glossary term. |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)()<br>Delete all glossary terms. |
| [disassociate](disassociate.html) | [jvm]<br>abstract suspend fun [disassociate](disassociate.html)(sourceId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), targetId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Remove the association between two terms. |
| [get](get.html) | [jvm]<br>abstract suspend fun [get](get.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?<br>Get a term by its lemma and language |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get all glossary terms. |
| [getAllAssociated](get-all-associated.html) | [jvm]<br>abstract suspend fun [getAllAssociated](get-all-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get all the terms associated with the given one, regardless of the language. |
| [getAssociated](get-associated.html) | [jvm]<br>abstract suspend fun [getAssociated](get-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), otherLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Get all the terms associated with the given one. |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?<br>Get a term by ID. |
| [isStillReferenced](is-still-referenced.html) | [jvm]<br>abstract suspend fun [isStillReferenced](is-still-referenced.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Determine whether a term still referenced by at least another term in the glossary. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html))<br>Update a glossary term. |

