---
title: ValidatePlaceholdersUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.usecase](../index.html)/[ValidatePlaceholdersUseCase](index.html)



# ValidatePlaceholdersUseCase



[jvm]\
interface [ValidatePlaceholdersUseCase](index.html)

Contract for the placeholder validation use case.



## Types


| Name | Summary |
|---|---|
| [Output](-output/index.html) | [jvm]<br>interface [Output](-output/index.html)<br>Result of the validation. |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(pairs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html), [SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;&gt;): [ValidatePlaceholdersUseCase.Output](-output/index.html) |

