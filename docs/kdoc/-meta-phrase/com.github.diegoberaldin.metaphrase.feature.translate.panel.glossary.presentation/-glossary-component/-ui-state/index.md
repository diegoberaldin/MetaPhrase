---
title: UiState
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation](../../index.html)/[GlossaryComponent](../index.html)/[UiState](index.html)



# UiState



[jvm]\
data class [UiState](index.html)(val sourceFlag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val targetFlag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, val isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val isBaseLanguage: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val terms: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;&gt;&gt; = emptyList())

Glossary panel UI state.



## Constructors


| | |
|---|---|
| [UiState](-ui-state.html) | [jvm]<br>constructor(sourceFlag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, targetFlag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = &quot;&quot;, isLoading: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, isBaseLanguage: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, terms: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;&gt;&gt; = emptyList())<br>Create [UiState](index.html) |


## Properties


| Name | Summary |
|---|---|
| [isBaseLanguage](is-base-language.html) | [jvm]<br>val [isBaseLanguage](is-base-language.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>indicating whether this is the base (deprecated) glossary panel, now it is displayed only for target languages |
| [isLoading](is-loading.html) | [jvm]<br>val [isLoading](is-loading.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false<br>indicating whether there is a background operation in progress |
| [sourceFlag](source-flag.html) | [jvm]<br>val [sourceFlag](source-flag.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>flag of the source language |
| [targetFlag](target-flag.html) | [jvm]<br>val [targetFlag](target-flag.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>flag of the target language |
| [terms](terms.html) | [jvm]<br>val [terms](terms.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[GlossaryTermModel](../../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)&gt;&gt;&gt;<br>terms to show (1 source term for *n* target terms) |

