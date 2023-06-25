---
title: getByKey
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[SegmentDao](index.html)/[getByKey](get-by-key.html)



# getByKey



[jvm]\
abstract suspend fun [getByKey](get-by-key.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?



Get a segment by key given its language within a project. There can only be at most one segment with a given key for any given language (compound index).



#### Return



[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html) or null



#### Parameters


jvm

| | |
|---|---|
| key | segment key |
| languageId | Language ID |




