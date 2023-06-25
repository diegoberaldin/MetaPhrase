---
title: ExportResourcesUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats](../index.html)/[ExportResourcesUseCase](index.html)



# ExportResourcesUseCase



[jvm]\
interface [ExportResourcesUseCase](index.html)

Contracf for the export resources use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Export a list of segments as a resouce file in a given fromat. |

