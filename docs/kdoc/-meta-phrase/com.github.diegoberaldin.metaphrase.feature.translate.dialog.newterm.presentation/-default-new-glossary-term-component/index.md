---
title: DefaultNewGlossaryTermComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](../index.html)/[DefaultNewGlossaryTermComponent](index.html)



# DefaultNewGlossaryTermComponent



[jvm]\
class [DefaultNewGlossaryTermComponent](index.html)(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html), mvi: [DefaultMviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-default-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](../-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](../-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](../-new-glossary-term-component/-effect/index.html)&gt; = DefaultMviModel(
        NewGlossaryTermComponent.UiState(),
    )) : [NewGlossaryTermComponent](../-new-glossary-term-component/index.html), [MviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](../-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](../-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](../-new-glossary-term-component/-effect/index.html)&gt; , ComponentContext



## Constructors


| | |
|---|---|
| [DefaultNewGlossaryTermComponent](-default-new-glossary-term-component.html) | [jvm]<br>constructor(componentContext: ComponentContext, coroutineContext: [CoroutineContext](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.coroutines/-coroutine-context/index.html), dispatchers: [CoroutineDispatcherProvider](../../com.github.diegoberaldin.metaphrase.core.common.coroutines/-coroutine-dispatcher-provider/index.html), mvi: [DefaultMviModel](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-default-mvi-model/index.html)&lt;[NewGlossaryTermComponent.Intent](../-new-glossary-term-component/-intent/index.html), [NewGlossaryTermComponent.UiState](../-new-glossary-term-component/-ui-state/index.html), [NewGlossaryTermComponent.Effect](../-new-glossary-term-component/-effect/index.html)&gt; = DefaultMviModel(         NewGlossaryTermComponent.UiState(),     )) |


## Properties


| Name | Summary |
|---|---|
| [backHandler](index.html#1029306802%2FProperties%2F2137835383) | [jvm]<br>open override val [backHandler](index.html#1029306802%2FProperties%2F2137835383): BackHandler |
| [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html) | [jvm]<br>open override val [effects](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/effects.html): SharedFlow&lt;[NewGlossaryTermComponent.Effect](../-new-glossary-term-component/-effect/index.html)&gt; |
| [instanceKeeper](index.html#-47807002%2FProperties%2F2137835383) | [jvm]<br>open override val [instanceKeeper](index.html#-47807002%2FProperties%2F2137835383): InstanceKeeper |
| [lifecycle](index.html#-1197012679%2FProperties%2F2137835383) | [jvm]<br>open override val [lifecycle](index.html#-1197012679%2FProperties%2F2137835383): Lifecycle |
| [stateKeeper](index.html#1128392690%2FProperties%2F2137835383) | [jvm]<br>open override val [stateKeeper](index.html#1128392690%2FProperties%2F2137835383): StateKeeper |
| [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html) | [jvm]<br>open override val [uiState](../../com.github.diegoberaldin.metaphrase.core.common.architecture/-mvi-model/ui-state.html): StateFlow&lt;[NewGlossaryTermComponent.UiState](../-new-glossary-term-component/-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [reduce](reduce.html) | [jvm]<br>open override fun [reduce](reduce.html)(intent: [NewGlossaryTermComponent.Intent](../-new-glossary-term-component/-intent/index.html)) |

