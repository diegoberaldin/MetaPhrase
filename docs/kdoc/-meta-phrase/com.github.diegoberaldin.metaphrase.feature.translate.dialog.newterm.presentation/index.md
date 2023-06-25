---
title: com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component/index.html) | [jvm]<br>class [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component/index.html)(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [NewGlossaryTermComponent](-new-glossary-term-component/index.html), ComponentContext |
| [GlossaryTermPair](-glossary-term-pair/index.html) | [jvm]<br>data class [GlossaryTermPair](-glossary-term-pair/index.html)(val sourceLemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val targetLemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Glossary term pair. |
| [NewGlossaryTermComponent](-new-glossary-term-component/index.html) | [jvm]<br>interface [NewGlossaryTermComponent](-new-glossary-term-component/index.html)<br>New glossary term component. |
| [NewGlossaryTermUiState](-new-glossary-term-ui-state/index.html) | [jvm]<br>data class [NewGlossaryTermUiState](-new-glossary-term-ui-state/index.html)(val sourceTerm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val sourceTermError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetTerm: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetTermError: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;)<br>New glossary term UI state. |

