---
title: com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation
---
//[MetaPhrase](../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component/index.html) | [jvm]<br>class [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component/index.html)(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html), mvi: [DefaultMviModel](../com.github.diegoberaldin.metaphrase.core.common.architecture/-default-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](-new-glossary-term-component/-effect/index.html)&gt; = DefaultMviModel(         NewGlossaryTermComponent.UiState(),     )) : [NewGlossaryTermComponent](-new-glossary-term-component/index.html), [MviModel](../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](-new-glossary-term-component/-effect/index.html)&gt; , ComponentContext |
| [GlossaryTermPair](-glossary-term-pair/index.html) | [jvm]<br>data class [GlossaryTermPair](-glossary-term-pair/index.html)(val sourceLemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val targetLemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Glossary term pair. |
| [NewGlossaryTermComponent](-new-glossary-term-component/index.html) | [jvm]<br>interface [NewGlossaryTermComponent](-new-glossary-term-component/index.html) : [MviModel](../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](-new-glossary-term-component/-effect/index.html)&gt; <br>New glossary term component contract. |

