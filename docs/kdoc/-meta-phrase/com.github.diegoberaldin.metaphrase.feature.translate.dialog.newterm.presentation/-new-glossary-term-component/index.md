---
title: NewGlossaryTermComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation](../index.html)/[NewGlossaryTermComponent](index.html)



# NewGlossaryTermComponent

interface [NewGlossaryTermComponent](index.html)

New glossary term component.



#### Inheritors


| |
|---|
| [DefaultNewGlossaryTermComponent](../-default-new-glossary-term-component/index.html) |


## Properties


| Name | Summary |
|---|---|
| [done](done.html) | [jvm]<br>abstract val [done](done.html): SharedFlow&lt;[GlossaryTermPair](../-glossary-term-pair/index.html)&gt;<br>Event emitted after successful submission. |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[NewGlossaryTermUiState](../-new-glossary-term-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [setSourceTerm](set-source-term.html) | [jvm]<br>abstract fun [setSourceTerm](set-source-term.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set source term. |
| [setTargetTerm](set-target-term.html) | [jvm]<br>abstract fun [setTargetTerm](set-target-term.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set target term. |
| [submit](submit.html) | [jvm]<br>abstract fun [submit](submit.html)()<br>Confirm the inserted term pair.. |

