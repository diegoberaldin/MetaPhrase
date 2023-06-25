---
title: getAssociated
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.glossary.repository](../index.html)/[GlossaryTermRepository](index.html)/[getAssociated](get-associated.html)



# getAssociated



[jvm]\
abstract suspend fun [getAssociated](get-associated.html)(model: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), otherLang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;



Get all the terms associated with the given one.



#### Return



list of associated terms in the target language (if any)



#### Parameters


jvm

| | |
|---|---|
| model | Source term |
| otherLang | Target language code |




