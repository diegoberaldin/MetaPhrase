---
title: createBatch
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[SegmentDao](index.html)/[createBatch](create-batch.html)



# createBatch



[jvm]\
abstract suspend fun [createBatch](create-batch.html)(models: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))



Create multiple segments in a single transaction.



#### Parameters


jvm

| | |
|---|---|
| models | segments to create |
| languageId | Language ID |




