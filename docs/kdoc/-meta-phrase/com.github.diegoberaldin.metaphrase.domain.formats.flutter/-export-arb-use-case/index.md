---
title: ExportArbUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.flutter](../index.html)/[ExportArbUseCase](index.html)



# ExportArbUseCase



[jvm]\
interface [ExportArbUseCase](index.html)

Contract for the export ARB resources use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into the ARB format and save it to a destination. |
