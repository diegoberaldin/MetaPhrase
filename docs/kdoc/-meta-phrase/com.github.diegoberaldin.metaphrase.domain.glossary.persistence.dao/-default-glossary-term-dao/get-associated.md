---
title: getAssociated
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao](../index.html)/[DefaultGlossaryTermDao](index.html)/[getAssociated](get-associated.html)



# getAssociated



[jvm]\
open suspend override fun [getAssociated](get-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), otherLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;



Get the terms associated with a given term.



#### Return



list of associated terms



#### Parameters


jvm

| | |
|---|---|
| model | Term (source) |
| otherLang | Target language code |




