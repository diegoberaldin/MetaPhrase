---
title: search
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.repository](../index.html)/[SegmentRepository](index.html)/[search](search.html)



# search



[jvm]\
abstract suspend fun [search](search.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), baseLanguageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) = TranslationUnitTypeFilter.ALL, search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, skip: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, limit: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;



Get all the segments corresponding to a set of search criteria.



#### Return



the list of matching segments



#### Parameters


jvm

| | |
|---|---|
| languageId | Target language ID |
| baseLanguageId | Source language ID |
| filter | Message type filter |
| search | Textual query (on the key, source text or target text) |
| skip | offset for pagination |
| limit | page size for pagination, if set to `0` pagination is ignored |




