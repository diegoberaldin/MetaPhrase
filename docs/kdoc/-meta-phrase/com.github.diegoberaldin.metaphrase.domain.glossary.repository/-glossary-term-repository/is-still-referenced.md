---
title: isStillReferenced
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.repository](../index.html)/[GlossaryTermRepository](index.html)/[isStillReferenced](is-still-referenced.html)



# isStillReferenced



[jvm]\
abstract suspend fun [isStillReferenced](is-still-referenced.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)



Determine whether a term still referenced by at least another term in the glossary.



#### Return



true if there is at least another term associated with it, false otherwise



#### Parameters


jvm

| | |
|---|---|
| id | Term ID |




