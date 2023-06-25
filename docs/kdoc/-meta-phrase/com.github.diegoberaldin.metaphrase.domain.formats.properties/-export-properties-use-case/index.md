---
title: ExportPropertiesUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.properties](../index.html)/[ExportPropertiesUseCase](index.html)



# ExportPropertiesUseCase

interface [ExportPropertiesUseCase](index.html)

Contract for the export properties resources use case.



#### Inheritors


| |
|---|
| [DefaultExportPropertiesUseCase](../-default-export-properties-use-case/index.html) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into Java properties format and save it to a destination. |

