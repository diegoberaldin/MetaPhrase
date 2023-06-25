---
title: ImportResourcesUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats](../index.html)/[ImportResourcesUseCase](index.html)



# ImportResourcesUseCase



[jvm]\
interface [ImportResourcesUseCase](index.html)

Contract for the import resources use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Import the content of a resource file as a list of segments. |

