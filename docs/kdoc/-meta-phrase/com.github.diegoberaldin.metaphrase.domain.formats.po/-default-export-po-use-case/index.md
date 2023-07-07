---
title: DefaultExportPoUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[DefaultExportPoUseCase](index.html)



# DefaultExportPoUseCase



[jvm]\
class [DefaultExportPoUseCase](index.html)(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [ExportPoUseCase](../-export-po-use-case/index.html)



## Constructors


| | |
|---|---|
| [DefaultExportPoUseCase](-default-export-po-use-case.html) | [jvm]<br>constructor(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>open suspend operator override fun [invoke](invoke.html)(segments: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;, path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Serialize the list of segments into the Gettext PO format and save it to a destination. |

