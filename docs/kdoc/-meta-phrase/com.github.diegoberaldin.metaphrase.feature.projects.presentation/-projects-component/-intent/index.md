---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.presentation](../../index.html)/[ProjectsComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

#### Inheritors


| |
|---|
| [Open](-open/index.html) |
| [CloseCurrentProject](-close-current-project/index.html) |
| [SaveCurrentProject](-save-current-project/index.html) |
| [Import](-import/index.html) |
| [Export](-export/index.html) |
| [MoveToPrevious](-move-to-previous/index.html) |
| [MoveToNext](-move-to-next/index.html) |
| [EndEditing](-end-editing/index.html) |
| [CopyBase](-copy-base/index.html) |
| [AddSegment](-add-segment/index.html) |
| [DeleteSegment](-delete-segment/index.html) |
| [ExportTmx](-export-tmx/index.html) |
| [ValidatePlaceholders](-validate-placeholders/index.html) |
| [InsertBestMatch](-insert-best-match/index.html) |
| [GlobalSpellcheck](-global-spellcheck/index.html) |
| [SyncWithTm](-sync-with-tm/index.html) |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) |
| [MachineTranslationInsert](-machine-translation-insert/index.html) |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) |
| [MachineTranslationShare](-machine-translation-share/index.html) |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddSegment](-add-segment/index.html) | [jvm]<br>object [AddSegment](-add-segment/index.html) : [ProjectsComponent.Intent](index.html)<br>Add a new segment (trigger dialog) |
| [CloseCurrentProject](-close-current-project/index.html) | [jvm]<br>object [CloseCurrentProject](-close-current-project/index.html) : [ProjectsComponent.Intent](index.html)<br>Close the current project. |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [ProjectsComponent.Intent](index.html)<br>Copy the base (source) message to the target editor field. |
| [DeleteSegment](-delete-segment/index.html) | [jvm]<br>object [DeleteSegment](-delete-segment/index.html) : [ProjectsComponent.Intent](index.html)<br>Delete the current segment. |
| [EndEditing](-end-editing/index.html) | [jvm]<br>object [EndEditing](-end-editing/index.html) : [ProjectsComponent.Intent](index.html)<br>Close the current editing operation. |
| [Export](-export/index.html) | [jvm]<br>data class [Export](-export/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [ProjectsComponent.Intent](index.html)<br>Export the messages of the current language to a resource file. |
| [ExportTmx](-export-tmx/index.html) | [jvm]<br>data class [ExportTmx](-export-tmx/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ProjectsComponent.Intent](index.html)<br>Export the TM content to a  TMX file. |
| [GlobalSpellcheck](-global-spellcheck/index.html) | [jvm]<br>object [GlobalSpellcheck](-global-spellcheck/index.html) : [ProjectsComponent.Intent](index.html)<br>Starts a global spellcheck validation. |
| [Import](-import/index.html) | [jvm]<br>data class [Import](-import/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [ProjectsComponent.Intent](index.html)<br>Import messages for the current language from a resource file. |
| [InsertBestMatch](-insert-best-match/index.html) | [jvm]<br>object [InsertBestMatch](-insert-best-match/index.html) : [ProjectsComponent.Intent](index.html)<br>Insert the best TM match into the translation editor. |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) | [jvm]<br>object [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) : [ProjectsComponent.Intent](index.html)<br>Share the whole content of the project with the TM provider. |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) | [jvm]<br>object [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) : [ProjectsComponent.Intent](index.html)<br>Copy the target message in the editor to the TM suggestion. |
| [MachineTranslationInsert](-machine-translation-insert/index.html) | [jvm]<br>object [MachineTranslationInsert](-machine-translation-insert/index.html) : [ProjectsComponent.Intent](index.html)<br>Insert the MT suggestion in the translation editor field. |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) | [jvm]<br>object [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) : [ProjectsComponent.Intent](index.html)<br>Retrieve a suggestion from the MT provider. |
| [MachineTranslationShare](-machine-translation-share/index.html) | [jvm]<br>object [MachineTranslationShare](-machine-translation-share/index.html) : [ProjectsComponent.Intent](index.html)<br>Share the current suggestion to the TM provider. |
| [MoveToNext](-move-to-next/index.html) | [jvm]<br>object [MoveToNext](-move-to-next/index.html) : [ProjectsComponent.Intent](index.html)<br>Move the cursor to the next message. |
| [MoveToPrevious](-move-to-previous/index.html) | [jvm]<br>object [MoveToPrevious](-move-to-previous/index.html) : [ProjectsComponent.Intent](index.html)<br>Move the cursor to the previous message. |
| [Open](-open/index.html) | [jvm]<br>data class [Open](-open/index.html)(val projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [ProjectsComponent.Intent](index.html)<br>Open a project with a given ID. |
| [SaveCurrentProject](-save-current-project/index.html) | [jvm]<br>data class [SaveCurrentProject](-save-current-project/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [ProjectsComponent.Intent](index.html)<br>Save the current project. |
| [SyncWithTm](-sync-with-tm/index.html) | [jvm]<br>object [SyncWithTm](-sync-with-tm/index.html) : [ProjectsComponent.Intent](index.html)<br>Saves all the messages of the current project in the global TM. |
| [ValidatePlaceholders](-validate-placeholders/index.html) | [jvm]<br>object [ValidatePlaceholders](-validate-placeholders/index.html) : [ProjectsComponent.Intent](index.html)<br>Start a placeholder validation. |

