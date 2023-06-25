---
title: ExportAndroidResourcesUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.android](../index.html)/[ExportAndroidResourcesUseCase](index.html)



# ExportAndroidResourcesUseCase



[jvm]\
interface [ExportAndroidResourcesUseCase](index.html)

Contract for the export Android resources use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into the Android XML format and save it to a destination. |

