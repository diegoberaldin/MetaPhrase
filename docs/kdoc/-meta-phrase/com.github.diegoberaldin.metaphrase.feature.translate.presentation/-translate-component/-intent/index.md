---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../../index.html)/[TranslateComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [Save](-save/index.html) |
| [Import](-import/index.html) |
| [Export](-export/index.html) |
| [MoveToPrevious](-move-to-previous/index.html) |
| [MoveToNext](-move-to-next/index.html) |
| [EndEditing](-end-editing/index.html) |
| [CopyBase](-copy-base/index.html) |
| [AddSegment](-add-segment/index.html) |
| [DeleteSegment](-delete-segment/index.html) |
| [CloseDialog](-close-dialog/index.html) |
| [TogglePanel](-toggle-panel/index.html) |
| [TryLoadSimilarities](-try-load-similarities/index.html) |
| [TryLoadGlossary](-try-load-glossary/index.html) |
| [TryLoadMachineTranslation](-try-load-machine-translation/index.html) |
| [ExportTmx](-export-tmx/index.html) |
| [ValidatePlaceholders](-validate-placeholders/index.html) |
| [InsertBestMatch](-insert-best-match/index.html) |
| [GlobalSpellcheck](-global-spellcheck/index.html) |
| [SyncWithTm](-sync-with-tm/index.html) |
| [AddGlossaryTerm](-add-glossary-term/index.html) |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) |
| [MachineTranslationInsert](-machine-translation-insert/index.html) |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) |
| [MachineTranslationShare](-machine-translation-share/index.html) |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddGlossaryTerm](-add-glossary-term/index.html) | [jvm]<br>data class [AddGlossaryTerm](-add-glossary-term/index.html)(val source: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, val target: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?) : [TranslateComponent.Intent](index.html)<br>Add a new glossary term. |
| [AddSegment](-add-segment/index.html) | [jvm]<br>object [AddSegment](-add-segment/index.html) : [TranslateComponent.Intent](index.html)<br>Add a new segment (opens dialog). |
| [CloseDialog](-close-dialog/index.html) | [jvm]<br>object [CloseDialog](-close-dialog/index.html) : [TranslateComponent.Intent](index.html)<br>Close any opened dialog. |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [TranslateComponent.Intent](index.html)<br>Copy the base (source) message to the translation field in the editor. |
| [DeleteSegment](-delete-segment/index.html) | [jvm]<br>object [DeleteSegment](-delete-segment/index.html) : [TranslateComponent.Intent](index.html)<br>Delete the current segment. |
| [EndEditing](-end-editing/index.html) | [jvm]<br>object [EndEditing](-end-editing/index.html) : [TranslateComponent.Intent](index.html)<br>End the current editing operation. |
| [Export](-export/index.html) | [jvm]<br>data class [Export](-export/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [TranslateComponent.Intent](index.html)<br>Export the current language messages to a resource file. |
| [ExportTmx](-export-tmx/index.html) | [jvm]<br>data class [ExportTmx](-export-tmx/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [TranslateComponent.Intent](index.html)<br>Export the global TM as a TMX file. |
| [GlobalSpellcheck](-global-spellcheck/index.html) | [jvm]<br>object [GlobalSpellcheck](-global-spellcheck/index.html) : [TranslateComponent.Intent](index.html)<br>Start a global spellcheck validation. |
| [Import](-import/index.html) | [jvm]<br>data class [Import](-import/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [TranslateComponent.Intent](index.html)<br>Import a list of message for the current language from a resource file. |
| [InsertBestMatch](-insert-best-match/index.html) | [jvm]<br>object [InsertBestMatch](-insert-best-match/index.html) : [TranslateComponent.Intent](index.html)<br>Insert the best match from translation memory in the translation field of the editor. |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) | [jvm]<br>object [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) : [TranslateComponent.Intent](index.html)<br>Share the whole project content (all messages for all languages) with the machine translation provider. |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) | [jvm]<br>object [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) : [TranslateComponent.Intent](index.html)<br>Copy the current translation of the editor into the MT suggestion. |
| [MachineTranslationInsert](-machine-translation-insert/index.html) | [jvm]<br>object [MachineTranslationInsert](-machine-translation-insert/index.html) : [TranslateComponent.Intent](index.html)<br>Copy the MT suggestion into the translation editor. |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) | [jvm]<br>object [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) : [TranslateComponent.Intent](index.html)<br>Retrieve a suggestion for the current message from the machine translation provider. In order for this to work, the [TryLoadMachineTranslation](-try-load-machine-translation/index.html) intent should have been sent earlier. |
| [MachineTranslationShare](-machine-translation-share/index.html) | [jvm]<br>object [MachineTranslationShare](-machine-translation-share/index.html) : [TranslateComponent.Intent](index.html)<br>Share the current suggestion (after editing) with the machine translation provider. |
| [MoveToNext](-move-to-next/index.html) | [jvm]<br>object [MoveToNext](-move-to-next/index.html) : [TranslateComponent.Intent](index.html)<br>Moves the cursor to the next message. |
| [MoveToPrevious](-move-to-previous/index.html) | [jvm]<br>object [MoveToPrevious](-move-to-previous/index.html) : [TranslateComponent.Intent](index.html)<br>Moves the cursor to the previous message. |
| [Save](-save/index.html) | [jvm]<br>data class [Save](-save/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [TranslateComponent.Intent](index.html)<br>Save the current project to a TMX file. |
| [SyncWithTm](-sync-with-tm/index.html) | [jvm]<br>object [SyncWithTm](-sync-with-tm/index.html) : [TranslateComponent.Intent](index.html)<br>Export all the segments of the current project to the global translation memory. |
| [TogglePanel](-toggle-panel/index.html) | [jvm]<br>data class [TogglePanel](-toggle-panel/index.html)(val config: [TranslateComponent.PanelConfig](../-panel-config/index.html)) : [TranslateComponent.Intent](index.html)<br>Toggle a bottom panel for a given section) |
| [TryLoadGlossary](-try-load-glossary/index.html) | [jvm]<br>object [TryLoadGlossary](-try-load-glossary/index.html) : [TranslateComponent.Intent](index.html)<br>Try and load terms from the glossary for the currently opened message. |
| [TryLoadMachineTranslation](-try-load-machine-translation/index.html) | [jvm]<br>object [TryLoadMachineTranslation](-try-load-machine-translation/index.html) : [TranslateComponent.Intent](index.html)<br>Loads the source version of the current message in machine translation component. (This does not retrieve any result from the MT provider, use [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) after having called this method) |
| [TryLoadSimilarities](-try-load-similarities/index.html) | [jvm]<br>object [TryLoadSimilarities](-try-load-similarities/index.html) : [TranslateComponent.Intent](index.html)<br>Try and load similarities from the translation memory for the currently opened message. |
| [ValidatePlaceholders](-validate-placeholders/index.html) | [jvm]<br>object [ValidatePlaceholders](-validate-placeholders/index.html) : [TranslateComponent.Intent](index.html)<br>Start a placeholder validation. |

