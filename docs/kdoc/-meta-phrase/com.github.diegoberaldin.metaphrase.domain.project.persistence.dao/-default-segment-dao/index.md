---
title: DefaultSegmentDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[DefaultSegmentDao](index.html)



# DefaultSegmentDao



[jvm]\
class [DefaultSegmentDao](index.html) : [SegmentDao](../-segment-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultSegmentDao](-default-segment-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new segment. |
| [createBatch](create-batch.html) | [jvm]<br>open suspend override fun [createBatch](create-batch.html)(models: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Create multiple segments in a single transaction. |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html))<br>Delete a segment. |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get all segments for a language within a project. |
| [getById](get-by-id.html) | [jvm]<br>open suspend override fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?<br>Get a segment by ID. |
| [getByKey](get-by-key.html) | [jvm]<br>open suspend override fun [getByKey](get-by-key.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)?<br>Get a segment by key given its language within a project. There can only be at most one segment with a given key for any given language (compound index). |
| [getUntranslatable](get-untranslatable.html) | [jvm]<br>open suspend override fun [getUntranslatable](get-untranslatable.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get all the untranslatable segments for a language within a project. |
| [search](search.html) | [jvm]<br>open suspend override fun [search](search.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), baseLanguageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html), search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, skip: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), limit: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Get the list of segments corresponding to some search criteria. |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html))<br>Update a segment. |

