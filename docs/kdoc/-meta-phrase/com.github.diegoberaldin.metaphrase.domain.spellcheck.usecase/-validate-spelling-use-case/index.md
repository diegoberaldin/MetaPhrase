---
title: ValidateSpellingUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase](../index.html)/[ValidateSpellingUseCase](index.html)



# ValidateSpellingUseCase

interface [ValidateSpellingUseCase](index.html)

Contract of the validate spelling use case.



#### Inheritors


| |
|---|
| [DefaultValidateSpellingUseCase](../-default-validate-spelling-use-case/index.html) |


## Types


| Name | Summary |
|---|---|
| [InputItem](-input-item/index.html) | [jvm]<br>data class [InputItem](-input-item/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Input parameter object. |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>abstract suspend operator fun [invoke](invoke.html)(input: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ValidateSpellingUseCase.InputItem](-input-item/index.html)&gt;, lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;<br>Check for spelling errors a given messaeg. |

