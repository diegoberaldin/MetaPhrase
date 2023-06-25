---
title: MachineTranslationComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../index.html)/[MachineTranslationComponent](index.html)



# MachineTranslationComponent



[jvm]\
interface [MachineTranslationComponent](index.html)

Machine translation component.



## Properties


| Name | Summary |
|---|---|
| [copySourceEvents](copy-source-events.html) | [jvm]<br>abstract val [copySourceEvents](copy-source-events.html): SharedFlow&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;<br>Events triggered when the content of the suggestion field from MT needs to be copied into the translation editor. |
| [copyTargetEvents](copy-target-events.html) | [jvm]<br>abstract val [copyTargetEvents](copy-target-events.html): SharedFlow&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;<br>Events triggered when the content of the translation editor needs to be copied to the suggestion field. |
| [shareEvents](share-events.html) | [jvm]<br>abstract val [shareEvents](share-events.html): SharedFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>Events triggered after a message share with the MT provider (true if successful, false otherwise) |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[MachineTranslationUiState](../-machine-translation-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [clear](clear.html) | [jvm]<br>abstract fun [clear](clear.html)()<br>Clear the content of the panel. |
| [copyTarget](copy-target.html) | [jvm]<br>abstract fun [copyTarget](copy-target.html)()<br>Signal the user intention to copy the content of the target field in the translation editor into the suggestion field, triggering a [copyTargetEvents](copy-target-events.html) event. |
| [copyTranslation](copy-translation.html) | [jvm]<br>abstract fun [copyTranslation](copy-translation.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Programmatically update the value of the suggestion. |
| [insertTranslation](insert-translation.html) | [jvm]<br>abstract fun [insertTranslation](insert-translation.html)()<br>Signal the user intention to copy the suggestion into the editor, triggering a [copySourceEvents](copy-source-events.html) event. |
| [load](load.html) | [jvm]<br>abstract fun [load](load.html)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Load the data for the message with a given key. No suggestion is retrieved until the [retrieve](retrieve.html) method is called. This is intended to reduce the request number and not exceed the service quota. |
| [retrieve](retrieve.html) | [jvm]<br>abstract fun [retrieve](retrieve.html)()<br>Retrieve a suggestion from the MT provider. The [load](load.html) method should be called to set the language and the source message that will be translated. |
| [setTranslation](set-translation.html) | [jvm]<br>abstract fun [setTranslation](set-translation.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Set a value for the suggestion, when the event is initiated by the user. |
| [share](share.html) | [jvm]<br>abstract fun [share](share.html)()<br>Share the suggestion field content with the remote MT provider. |

