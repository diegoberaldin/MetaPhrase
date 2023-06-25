---
title: GetSimilaritiesUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.usecase](../index.html)/[GetSimilaritiesUseCase](index.html)



# GetSimilaritiesUseCase



[jvm]\
interface [GetSimilaritiesUseCase](index.html)

Contract for the get similarities use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segment: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), threshold: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.75f): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt;<br>Get a series of matches from the translation memory or the current project for a given segment. |

