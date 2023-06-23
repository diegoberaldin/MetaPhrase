---
title: GlossaryComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation](../index.html)/[GlossaryComponent](index.html)



# GlossaryComponent



[jvm]\
interface [GlossaryComponent](index.html)



## Properties


| Name | Summary |
|---|---|
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[GlossaryUiState](../-glossary-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [addSourceTerm](add-source-term.html) | [jvm]<br>abstract fun [addSourceTerm](add-source-term.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [addTargetTerm](add-target-term.html) | [jvm]<br>abstract fun [addTargetTerm](add-target-term.html)(lemma: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), source: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)) |
| [clear](clear.html) | [jvm]<br>abstract fun [clear](clear.html)() |
| [deleteTerm](delete-term.html) | [jvm]<br>abstract fun [deleteTerm](delete-term.html)(term: [GlossaryTermModel](../../com.github.diegoberaldin.metaphrase.domain.glossary.data/-glossary-term-model/index.html)) |
| [load](load.html) | [jvm]<br>abstract fun [load](load.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |

