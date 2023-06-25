---
title: isStillReferenced
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao](../index.html)/[GlossaryTermDao](index.html)/[isStillReferenced](is-still-referenced.html)



# isStillReferenced



[jvm]\
abstract suspend fun [isStillReferenced](is-still-referenced.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Determine whether a term is still referenced by any other term in the glossary.



#### Return



true if there is still at least one other term referencing this one



#### Parameters


jvm

| | |
|---|---|
| id | Term ID |




