---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats](../index.html)/[ExportResourcesUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))



Export a list of segments as a resouce file in a given fromat.



#### Parameters


jvm

| | |
|---|---|
| segments | Segments to export |
| path | Destination path |
| lang | Language code |
| type | Resource type |



