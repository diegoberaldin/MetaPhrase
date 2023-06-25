---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.tm.usecase](../index.html)/[GetSimilaritiesUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(segment: [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), threshold: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html) = 0.75f): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[TranslationUnit](../../com.github.diegoberaldin.metaphrase.domain.project.data/-translation-unit/index.html)&gt;



Get a series of matches from the translation memory or the current project for a given segment.



#### Return



list of matching translation units



#### Parameters


jvm

| | |
|---|---|
| segment | source message |
| projectId | Project ID |
| languageId | Language ID |
| threshold | minimum similarity threshold |




