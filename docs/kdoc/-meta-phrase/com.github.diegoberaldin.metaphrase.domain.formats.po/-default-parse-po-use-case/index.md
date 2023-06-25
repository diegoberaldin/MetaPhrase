---
title: DefaultParsePoUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.formats.po](../index.html)/[DefaultParsePoUseCase](index.html)



# DefaultParsePoUseCase



[jvm]\
class [DefaultParsePoUseCase](index.html)(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [ParsePoUseCase](../-parse-po-use-case/index.html)



## Constructors


| | |
|---|---|
| [DefaultParsePoUseCase](-default-parse-po-use-case.html) | [jvm]<br>constructor(dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>open suspend operator override fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[SegmentModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-segment-model/index.html)&gt;<br>Deserialize a list of messages from the Gettext PO format. |

