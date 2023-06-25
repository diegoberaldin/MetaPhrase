---
title: ParsePoUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[ParsePoUseCase](index.html)



# ParsePoUseCase

interface [ParsePoUseCase](index.html)

Contract for the parse Android resources use case.



#### Inheritors


| |
|---|
| [DefaultParsePoUseCase](../-default-parse-po-use-case/index.html) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Deserialize a list of messages from the Gettext PO format. |

