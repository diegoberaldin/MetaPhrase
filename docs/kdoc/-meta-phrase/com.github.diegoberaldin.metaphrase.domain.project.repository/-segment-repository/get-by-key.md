---
title: getByKey
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.repository](../index.html)/[SegmentRepository](index.html)/[getByKey](get-by-key.html)



# getByKey



[jvm]\
abstract suspend fun [getByKey](get-by-key.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?



Get a segment by its key.



#### Return



[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html) or null if no such segment exists



#### Parameters


jvm

| | |
|---|---|
| key | Message key |
| languageId | Language ID |




