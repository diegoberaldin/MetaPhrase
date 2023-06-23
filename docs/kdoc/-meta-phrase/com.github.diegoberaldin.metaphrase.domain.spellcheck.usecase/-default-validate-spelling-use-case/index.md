---
title: DefaultValidateSpellingUseCase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase](../index.html)/[DefaultValidateSpellingUseCase](index.html)



# DefaultValidateSpellingUseCase



[jvm]\
class [DefaultValidateSpellingUseCase](index.html)(repository: [SpellCheckRepository](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.repo/-spell-check-repository/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [ValidateSpellingUseCase](../-validate-spelling-use-case/index.html)



## Constructors


| | |
|---|---|
| [DefaultValidateSpellingUseCase](-default-validate-spelling-use-case.html) | [jvm]<br>constructor(repository: [SpellCheckRepository](../../com.github.diegoberaldin.metaphrase.domain.spellcheck.repo/-spell-check-repository/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [invoke](invoke.html) | [jvm]<br>open suspend operator override fun [invoke](invoke.html)(input: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ValidateSpellingUseCase.InputItem](../-validate-spelling-use-case/-input-item/index.html)&gt;, lang: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt; |

