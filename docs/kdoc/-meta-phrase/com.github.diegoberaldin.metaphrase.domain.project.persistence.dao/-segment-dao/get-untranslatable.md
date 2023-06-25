---
title: getUntranslatable
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[SegmentDao](index.html)/[getUntranslatable](get-untranslatable.html)



# getUntranslatable



[jvm]\
abstract suspend fun [getUntranslatable](get-untranslatable.html)(languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;



Get all the untranslatable segments for a language within a project.



#### Return



list of non-translatable (source only) segments



#### Parameters


jvm

| | |
|---|---|
| languageId | Language ID |




