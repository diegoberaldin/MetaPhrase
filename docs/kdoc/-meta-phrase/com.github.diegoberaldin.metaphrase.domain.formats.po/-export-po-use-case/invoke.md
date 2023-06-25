---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[ExportPoUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))



Serialize the list of segments into the Gettext PO format and save it to a destination.



#### Parameters


jvm

| | |
|---|---|
| segments | Segments to be exported |
| path | Destination path |
| lang | language of the message to include in the PO header |




