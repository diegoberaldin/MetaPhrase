---
title: GetGlossaryTermsUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.usecase](../index.html)/[GetGlossaryTermsUseCase](index.html)



# GetGlossaryTermsUseCase



[jvm]\
interface [GetGlossaryTermsUseCase](index.html)

Contract for the get glossary terms use case.



## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;<br>Retrieve the glossary terms (source) for a source message. This should consider the stem of the words contained in the message. |

