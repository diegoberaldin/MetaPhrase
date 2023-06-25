---
title: ExportJsonUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.json](../index.html)/[ExportJsonUseCase](index.html)



# ExportJsonUseCase

interface [ExportJsonUseCase](index.html)

Contract for the export ngx-translate JSON resources use case.



#### Inheritors


| |
|---|
| [DefaultExportJsonUseCase](../-default-export-json-use-case/index.html) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into the ngx-translate JSON format and save it to a destination. |

