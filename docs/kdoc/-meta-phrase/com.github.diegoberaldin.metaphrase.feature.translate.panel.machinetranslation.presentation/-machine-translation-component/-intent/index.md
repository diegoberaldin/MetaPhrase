---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation](../../index.html)/[MachineTranslationComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [Clear](-clear/index.html) |
| [Load](-load/index.html) |
| [Retrieve](-retrieve/index.html) |
| [InsertTranslation](-insert-translation/index.html) |
| [CopyTarget](-copy-target/index.html) |
| [SetTranslation](-set-translation/index.html) |
| [CopyTranslation](-copy-translation/index.html) |
| [Share](-share/index.html) |


## Types


| Name | Summary |
|---|---|
| [Clear](-clear/index.html) | [jvm]<br>object [Clear](-clear/index.html) : [MachineTranslationComponent.Intent](index.html)<br>Clear the content of the panel. |
| [CopyTarget](-copy-target/index.html) | [jvm]<br>object [CopyTarget](-copy-target/index.html) : [MachineTranslationComponent.Intent](index.html)<br>Signal the user intention to copy the content of the target field in the translation editor into the suggestion field, triggering a [Effect.CopyTarget](../-effect/-copy-target/index.html) event. |
| [CopyTranslation](-copy-translation/index.html) | [jvm]<br>data class [CopyTranslation](-copy-translation/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MachineTranslationComponent.Intent](index.html)<br>Programmatically update the value of the suggestion. |
| [InsertTranslation](-insert-translation/index.html) | [jvm]<br>object [InsertTranslation](-insert-translation/index.html) : [MachineTranslationComponent.Intent](index.html)<br>Signal the user intention to copy the suggestion into the editor, triggering a [Effect.CopySource](../-effect/-copy-source/index.html) event. |
| [Load](-load/index.html) | [jvm]<br>data class [Load](-load/index.html)(val key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val languageId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [MachineTranslationComponent.Intent](index.html)<br>Load the data for the message with a given key. No suggestion is retrieved until the retrieve method is called. This is intended to reduce the request number and not exceed the service quota. |
| [Retrieve](-retrieve/index.html) | [jvm]<br>object [Retrieve](-retrieve/index.html) : [MachineTranslationComponent.Intent](index.html)<br>Retrieve a suggestion from the MT provider. The load method should be called to set the language and the source message that will be translated. |
| [SetTranslation](-set-translation/index.html) | [jvm]<br>data class [SetTranslation](-set-translation/index.html)(val value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [MachineTranslationComponent.Intent](index.html)<br>Set a value for the suggestion, when the event is initiated by the user. |
| [Share](-share/index.html) | [jvm]<br>object [Share](-share/index.html) : [MachineTranslationComponent.Intent](index.html)<br>Share the suggestion field content with the remote MT provider. |

