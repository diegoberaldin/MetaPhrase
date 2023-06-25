---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.usecase](../index.html)/[GetGlossaryTermsUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
abstract suspend operator fun [invoke](invoke.html)(message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;



Retrieve the glossary terms (source) for a source message. This should consider the stem of the words contained in the message.



#### Return



list of all the matches from the glossary for the text



#### Parameters


jvm

| | |
|---|---|
| message | Message text |
| lang | Language code |




