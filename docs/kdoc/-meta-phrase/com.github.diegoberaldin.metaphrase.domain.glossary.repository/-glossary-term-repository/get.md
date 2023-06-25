---
title: get
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.repository](../index.html)/[GlossaryTermRepository](index.html)/[get](get.html)



# get



[jvm]\
abstract suspend fun [get](get.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)?



Get a term by its lemma and language



#### Return



[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html) or null if no such term exists



#### Parameters


jvm

| | |
|---|---|
| lemma | Term lemma |
| lang | Language code |




