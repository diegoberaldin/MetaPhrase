---
title: ExportPoUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[ExportPoUseCase](index.html)



# ExportPoUseCase

interface [ExportPoUseCase](index.html)

Contract for the export PO resources use case.



#### Inheritors


| |
|---|
| [DefaultExportPoUseCase](../-default-export-po-use-case/index.html) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into the Gettext PO format and save it to a destination. |

