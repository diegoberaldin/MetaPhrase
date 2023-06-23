---
title: MachineTranslationComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../index.html)/[MachineTranslationComponent](index.html)



# MachineTranslationComponent



[jvm]\
interface [MachineTranslationComponent](index.html)



## Properties


| Name | Summary |
|---|---|
| [copySourceEvents](copy-source-events.html) | [jvm]<br>abstract val [copySourceEvents](copy-source-events.html): SharedFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [copyTargetEvents](copy-target-events.html) | [jvm]<br>abstract val [copyTargetEvents](copy-target-events.html): SharedFlow&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt; |
| [shareEvents](share-events.html) | [jvm]<br>abstract val [shareEvents](share-events.html): SharedFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[MachineTranslationUiState](../-machine-translation-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [clear](clear.html) | [jvm]<br>abstract fun [clear](clear.html)() |
| [copyTarget](copy-target.html) | [jvm]<br>abstract fun [copyTarget](copy-target.html)() |
| [copyTranslation](copy-translation.html) | [jvm]<br>abstract fun [copyTranslation](copy-translation.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [insertTranslation](insert-translation.html) | [jvm]<br>abstract fun [insertTranslation](insert-translation.html)() |
| [load](load.html) | [jvm]<br>abstract fun [load](load.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [retrieve](retrieve.html) | [jvm]<br>abstract fun [retrieve](retrieve.html)() |
| [setTranslation](set-translation.html) | [jvm]<br>abstract fun [setTranslation](set-translation.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [share](share.html) | [jvm]<br>abstract fun [share](share.html)() |

