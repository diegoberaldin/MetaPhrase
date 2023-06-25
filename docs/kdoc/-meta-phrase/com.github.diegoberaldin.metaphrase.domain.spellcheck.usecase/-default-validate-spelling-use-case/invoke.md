---
title: invoke
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase](../index.html)/[DefaultValidateSpellingUseCase](index.html)/[invoke](invoke.html)



# invoke



[jvm]\
open suspend operator override fun [invoke](invoke.html)(input: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ValidateSpellingUseCase.InputItem](../-validate-spelling-use-case/-input-item/index.html)&gt;, lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;



Check for spelling errors a given messaeg.



#### Return



errors in the form of a map from message key to list of misspelled words



#### Parameters


jvm

| | |
|---|---|
| input | operation input |
| lang | language code |




