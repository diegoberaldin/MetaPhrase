---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../index.html)/[ImportSegmentsUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, language: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))



Save a list of segments for a given language inside a given project.



#### Parameters


jvm

| | |
|---|---|
| segments | Segments to save |
| language | Language the segments belong to |
| projectId | Project ID |




