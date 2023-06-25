---
title: search
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[SegmentDao](index.html)/[search](search.html)



# search



[jvm]\
abstract suspend fun [search](search.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), baseLanguageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, filter: [TranslationUnitTypeFilter](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit-type-filter/index.html) = TranslationUnitTypeFilter.ALL, search: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null, skip: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, limit: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;



Get the list of segments corresponding to some search criteria.



#### Return



list of segment corresponding to the criteria



#### Parameters


jvm

| | |
|---|---|
| languageId | target language ID |
| baseLanguageId | source language ID |
| filter | message type filter |
| search | textual query |
| skip | offset for pagination |
| limit | page size for pagination, if set to `0` no pagination will be performed |




