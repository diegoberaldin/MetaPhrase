---
title: getBy
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao](../index.html)/[GlossaryTermDao](index.html)/[getBy](get-by.html)



# getBy



[jvm]\
abstract suspend fun [getBy](get-by.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?



Get a term given its lemma and language.



#### Return



[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html) or null if no such term exists



#### Parameters


jvm

| | |
|---|---|
| lemma | Term lemma |
| lang | Language code (ISO 639-1) |




