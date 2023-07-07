---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[DefaultExportPoUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
open suspend operator override fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))



Serialize the list of segments into the Gettext PO format and save it to a destination.



#### Parameters


jvm

| | |
|---|---|
| segments | Segments to be exported |
| path | Destination path |
| lang | language of the message to include in the PO header |




