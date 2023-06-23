---
title: DefaultNewGlossaryTermComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](../index.html)/[DefaultNewGlossaryTermComponent](index.html)



# DefaultNewGlossaryTermComponent



[jvm]\
class [DefaultNewGlossaryTermComponent](index.html)(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) : [NewGlossaryTermComponent](../-new-glossary-term-component/index.html), ComponentContext



## Constructors


| | |
|---|---|
| [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component.html) | [jvm]<br>constructor(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [backHandler](index.html#1029306802%2FProperties%2F2137835383) | [jvm]<br>open override val [backHandler](index.html#1029306802%2FProperties%2F2137835383): BackHandler |
| [done](done.html) | [jvm]<br>open override val [done](done.html): MutableSharedFlow&lt;[GlossaryTermPair](../-glossary-term-pair/index.html)&gt; |
| [instanceKeeper](index.html#-47807002%2FProperties%2F2137835383) | [jvm]<br>open override val [instanceKeeper](index.html#-47807002%2FProperties%2F2137835383): InstanceKeeper |
| [lifecycle](index.html#-1197012679%2FProperties%2F2137835383) | [jvm]<br>open override val [lifecycle](index.html#-1197012679%2FProperties%2F2137835383): Lifecycle |
| [stateKeeper](index.html#1128392690%2FProperties%2F2137835383) | [jvm]<br>open override val [stateKeeper](index.html#1128392690%2FProperties%2F2137835383): StateKeeper |
| [uiState](ui-state.html) | [jvm]<br>open lateinit override var [uiState](ui-state.html): StateFlow&lt;[NewGlossaryTermUiState](../-new-glossary-term-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [setSourceTerm](set-source-term.html) | [jvm]<br>open override fun [setSourceTerm](set-source-term.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [setTargetTerm](set-target-term.html) | [jvm]<br>open override fun [setTargetTerm](set-target-term.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [submit](submit.html) | [jvm]<br>open override fun [submit](submit.html)() |

