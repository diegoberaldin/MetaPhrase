---
title: DefaultExportGlossaryUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.usecase](../index.html)/[DefaultExportGlossaryUseCase](index.html)



# DefaultExportGlossaryUseCase



[jvm]\
class [DefaultExportGlossaryUseCase](index.html)(repository: [GlossaryTermRepository](../../com.github.diegoberaldin.metaphrase.domain.glossary.repository/-glossary-term-repository/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [ExportGlossaryUseCase](../-export-glossary-use-case/index.html)



## Constructors


| | |
|---|---|
| [DefaultExportGlossaryUseCase](-default-export-glossary-use-case.html) | [jvm]<br>constructor(repository: [GlossaryTermRepository](../../com.github.diegoberaldin.metaphrase.domain.glossary.repository/-glossary-term-repository/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>open suspend operator override fun [invoke](invoke.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Export the global glossary content as a CSV file at a given path.. |

