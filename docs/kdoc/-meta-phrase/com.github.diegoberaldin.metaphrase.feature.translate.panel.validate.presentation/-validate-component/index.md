---
title: ValidateComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation](../index.html)/[ValidateComponent](index.html)



# ValidateComponent



[jvm]\
interface [ValidateComponent](index.html)

Validation component.



## Properties


| Name | Summary |
|---|---|
| [selectionEvents](selection-events.html) | [jvm]<br>abstract val [selectionEvents](selection-events.html): SharedFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Events triggered when a reference to a message is selected with the key of the message |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[InvalidSegmentUiState](../-invalid-segment-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [clear](clear.html) | [jvm]<br>abstract fun [clear](clear.html)()<br>Clear the panel content. |
| [loadInvalidPlaceholders](load-invalid-placeholders.html) | [jvm]<br>abstract fun [loadInvalidPlaceholders](load-invalid-placeholders.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), invalidKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)<br>Load a list of invalid placeholder references. |
| [loadSpellingMistakes](load-spelling-mistakes.html) | [jvm]<br>abstract fun [loadSpellingMistakes](load-spelling-mistakes.html)(errors: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;&gt;)<br>Load a list of spelling mistake references. |
| [selectItem](select-item.html) | [jvm]<br>abstract fun [selectItem](select-item.html)(value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Select an reference, triggering [selectionEvents](selection-events.html). |

