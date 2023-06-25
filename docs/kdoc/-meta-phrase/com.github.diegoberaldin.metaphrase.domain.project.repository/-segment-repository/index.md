---
title: SegmentRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.repository](../index.html)/[SegmentRepository](index.html)



# SegmentRepository



[jvm]\
interface [SegmentRepository](index.html)

Contract for the segment repository.



## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new segment. |
| [createBatch](create-batch.html) | [jvm]<br>abstract suspend fun [createBatch](create-batch.html)(models: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Create multiple segments in a single transaction. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html))<br>Delete a segment. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get all segments for a given language within a given project. |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?<br>Get a segment by its ID. |
| [getByKey](get-by-key.html) | [jvm]<br>abstract suspend fun [getByKey](get-by-key.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?<br>Get a segment by its key. |
| [getUntranslatable](get-untranslatable.html) | [jvm]<br>abstract suspend fun [getUntranslatable](get-untranslatable.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get all untranslatable (source only) segments. |
| [search](search.html) | [jvm]<br>abstract suspend fun [search](search.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), baseLanguageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) = TranslationUnitTypeFilter.ALL, search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, skip: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, limit: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get all the segments corresponding to a set of search criteria. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html))<br>Update a segment. |

