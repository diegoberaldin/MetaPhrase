---
title: TranslationMemoryComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation](../index.html)/[TranslationMemoryComponent](index.html)



# TranslationMemoryComponent



[jvm]\
interface [TranslationMemoryComponent](index.html)

Translation memory component.



## Properties


| Name | Summary |
|---|---|
| [copyEvents](copy-events.html) | [jvm]<br>abstract val [copyEvents](copy-events.html): SharedFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Events triggered when a TM message should be copied into the translation editor |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[TranslationMemoryUiState](../-translation-memory-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [clear](clear.html) | [jvm]<br>abstract fun [clear](clear.html)()<br>Clear the content of the panel. |
| [copyTranslation](copy-translation.html) | [jvm]<br>abstract fun [copyTranslation](copy-translation.html)(index: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Copy the match with a given index into the translation field. |
| [load](load.html) | [jvm]<br>abstract fun [load](load.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Load the TM matches for the message with a given key. |

