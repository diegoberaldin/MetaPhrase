---
title: ParseResxUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.resx](../index.html)/[ParseResxUseCase](index.html)



# ParseResxUseCase



[jvm]\
interface [ParseResxUseCase](index.html)

Contract for the parse Android resources use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Deserialize a list of messages from the Windows resx format. |
