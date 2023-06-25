---
title: ImportSegmentsUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../index.html)/[ImportSegmentsUseCase](index.html)



# ImportSegmentsUseCase



[jvm]\
interface [ImportSegmentsUseCase](index.html)

Contract of the import segments use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, language: [LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Save a list of segments for a given language inside a given project. |

