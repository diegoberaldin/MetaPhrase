---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../../index.html)/[RootComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [OpenDialog](-open-dialog/index.html) |
| [OpenProject](-open-project/index.html) |
| [OpenEditProject](-open-edit-project/index.html) |
| [SaveCurrentProject](-save-current-project/index.html) |
| [SaveProjectAs](-save-project-as/index.html) |
| [SaveProject](-save-project/index.html) |
| [OpenNewDialog](-open-new-dialog/index.html) |
| [CloseDialog](-close-dialog/index.html) |
| [CloseCurrentProject](-close-current-project/index.html) |
| [ConfirmCloseCurrentProject](-confirm-close-current-project/index.html) |
| [OpenStatistics](-open-statistics/index.html) |
| [OpenSettings](-open-settings/index.html) |
| [OpenImportDialog](-open-import-dialog/index.html) |
| [OpenExportDialog](-open-export-dialog/index.html) |
| [Import](-import/index.html) |
| [Export](-export/index.html) |
| [MoveToPreviousSegment](-move-to-previous-segment/index.html) |
| [MoveToNextSegment](-move-to-next-segment/index.html) |
| [EndEditing](-end-editing/index.html) |
| [CopyBase](-copy-base/index.html) |
| [AddSegment](-add-segment/index.html) |
| [DeleteSegment](-delete-segment/index.html) |
| [OpenExportTmxDialog](-open-export-tmx-dialog/index.html) |
| [ExportTmx](-export-tmx/index.html) |
| [OpenImportTmxDialog](-open-import-tmx-dialog/index.html) |
| [ImportTmx](-import-tmx/index.html) |
| [ClearTm](-clear-tm/index.html) |
| [SyncTm](-sync-tm/index.html) |
| [ValidatePlaceholders](-validate-placeholders/index.html) |
| [InsertBestMatch](-insert-best-match/index.html) |
| [GlobalSpellcheck](-global-spellcheck/index.html) |
| [OpenImportGlossaryDialog](-open-import-glossary-dialog/index.html) |
| [ImportGlossary](-import-glossary/index.html) |
| [OpenExportGlossaryDialog](-open-export-glossary-dialog/index.html) |
| [ExportGlossary](-export-glossary/index.html) |
| [ClearGlossary](-clear-glossary/index.html) |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) |
| [MachineTranslationInsert](-machine-translation-insert/index.html) |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) |
| [MachineTranslationShare](-machine-translation-share/index.html) |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) |
| [OpenManual](-open-manual/index.html) |


## Types


| Name | Summary |
|---|---|
| [AddSegment](-add-segment/index.html) | [jvm]<br>object [AddSegment](-add-segment/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;New segment&quot; dialog. |
| [ClearGlossary](-clear-glossary/index.html) | [jvm]<br>object [ClearGlossary](-clear-glossary/index.html) : [RootComponent.Intent](index.html)<br>Clear the global glossary. |
| [ClearTm](-clear-tm/index.html) | [jvm]<br>object [ClearTm](-clear-tm/index.html) : [RootComponent.Intent](index.html)<br>Clear the content of the global Translation Memory. |
| [CloseCurrentProject](-close-current-project/index.html) | [jvm]<br>data class [CloseCurrentProject](-close-current-project/index.html)(val closeAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [RootComponent.Intent](index.html)<br>Displays the &quot;Confirm project close&quot; dialog. |
| [CloseDialog](-close-dialog/index.html) | [jvm]<br>object [CloseDialog](-close-dialog/index.html) : [RootComponent.Intent](index.html)<br>Close any opened dialog. |
| [ConfirmCloseCurrentProject](-confirm-close-current-project/index.html) | [jvm]<br>data class [ConfirmCloseCurrentProject](-confirm-close-current-project/index.html)(val openAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val newAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [RootComponent.Intent](index.html)<br>Sends user confirmation to close the current project with an optional action afterwards. |
| [CopyBase](-copy-base/index.html) | [jvm]<br>object [CopyBase](-copy-base/index.html) : [RootComponent.Intent](index.html)<br>Copy the base (source) message to the target message. |
| [DeleteSegment](-delete-segment/index.html) | [jvm]<br>object [DeleteSegment](-delete-segment/index.html) : [RootComponent.Intent](index.html)<br>Delete the current segment. |
| [EndEditing](-end-editing/index.html) | [jvm]<br>object [EndEditing](-end-editing/index.html) : [RootComponent.Intent](index.html)<br>End editing the current message. |
| [Export](-export/index.html) | [jvm]<br>data class [Export](-export/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.Intent](index.html)<br>Export messages to a resource file. |
| [ExportGlossary](-export-glossary/index.html) | [jvm]<br>data class [ExportGlossary](-export-glossary/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Export the global glossary to a CSV file. |
| [ExportTmx](-export-tmx/index.html) | [jvm]<br>data class [ExportTmx](-export-tmx/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Export the current TM content to a TMX file. |
| [GlobalSpellcheck](-global-spellcheck/index.html) | [jvm]<br>object [GlobalSpellcheck](-global-spellcheck/index.html) : [RootComponent.Intent](index.html)<br>Start a global spellcheck validation. |
| [Import](-import/index.html) | [jvm]<br>data class [Import](-import/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.Intent](index.html)<br>Import messages from a resource file. |
| [ImportGlossary](-import-glossary/index.html) | [jvm]<br>data class [ImportGlossary](-import-glossary/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Import a CSV file into the glossary. |
| [ImportTmx](-import-tmx/index.html) | [jvm]<br>data class [ImportTmx](-import-tmx/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Import a TMX file to the translation memory. |
| [InsertBestMatch](-insert-best-match/index.html) | [jvm]<br>object [InsertBestMatch](-insert-best-match/index.html) : [RootComponent.Intent](index.html)<br>Insert the best TM match to the translation editor. |
| [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) | [jvm]<br>object [MachineTranslationContributeTm](-machine-translation-contribute-tm/index.html) : [RootComponent.Intent](index.html)<br>Share the whole project as a TM to the machine translation provider. |
| [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) | [jvm]<br>object [MachineTranslationCopyTarget](-machine-translation-copy-target/index.html) : [RootComponent.Intent](index.html)<br>Copy the target message into the machine translation suggestion. |
| [MachineTranslationInsert](-machine-translation-insert/index.html) | [jvm]<br>object [MachineTranslationInsert](-machine-translation-insert/index.html) : [RootComponent.Intent](index.html)<br>Insert the current suggestion from machine translation in the editor. |
| [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) | [jvm]<br>object [MachineTranslationRetrieve](-machine-translation-retrieve/index.html) : [RootComponent.Intent](index.html)<br>Retrieve a suggestion for the current message from the machine translation provider. |
| [MachineTranslationShare](-machine-translation-share/index.html) | [jvm]<br>object [MachineTranslationShare](-machine-translation-share/index.html) : [RootComponent.Intent](index.html)<br>Share the current suggestion to the remote machine translation provider. |
| [MoveToNextSegment](-move-to-next-segment/index.html) | [jvm]<br>object [MoveToNextSegment](-move-to-next-segment/index.html) : [RootComponent.Intent](index.html)<br>Navigate to the next segment in the editor. |
| [MoveToPreviousSegment](-move-to-previous-segment/index.html) | [jvm]<br>object [MoveToPreviousSegment](-move-to-previous-segment/index.html) : [RootComponent.Intent](index.html)<br>Navigate to the previous segment in the editor. |
| [OpenDialog](-open-dialog/index.html) | [jvm]<br>object [OpenDialog](-open-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;Open project&quot; dialog. |
| [OpenEditProject](-open-edit-project/index.html) | [jvm]<br>object [OpenEditProject](-open-edit-project/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;Edit project&quot; dialog. |
| [OpenExportDialog](-open-export-dialog/index.html) | [jvm]<br>data class [OpenExportDialog](-open-export-dialog/index.html)(val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.Intent](index.html)<br>Open the export dialog for the current language. |
| [OpenExportGlossaryDialog](-open-export-glossary-dialog/index.html) | [jvm]<br>object [OpenExportGlossaryDialog](-open-export-glossary-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the export glossary dialog. |
| [OpenExportTmxDialog](-open-export-tmx-dialog/index.html) | [jvm]<br>object [OpenExportTmxDialog](-open-export-tmx-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the dialog to export the current TM content to TMX. |
| [OpenImportDialog](-open-import-dialog/index.html) | [jvm]<br>data class [OpenImportDialog](-open-import-dialog/index.html)(val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.Intent](index.html)<br>Open the import dialog for the current language. |
| [OpenImportGlossaryDialog](-open-import-glossary-dialog/index.html) | [jvm]<br>object [OpenImportGlossaryDialog](-open-import-glossary-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the import glossary dialog. |
| [OpenImportTmxDialog](-open-import-tmx-dialog/index.html) | [jvm]<br>object [OpenImportTmxDialog](-open-import-tmx-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the import dialog to populate the TM. |
| [OpenManual](-open-manual/index.html) | [jvm]<br>object [OpenManual](-open-manual/index.html) : [RootComponent.Intent](index.html)<br>Open the user manual. |
| [OpenNewDialog](-open-new-dialog/index.html) | [jvm]<br>object [OpenNewDialog](-open-new-dialog/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;New project&quot; dialog. |
| [OpenProject](-open-project/index.html) | [jvm]<br>data class [OpenProject](-open-project/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Loads a translation project from disk. |
| [OpenSettings](-open-settings/index.html) | [jvm]<br>object [OpenSettings](-open-settings/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;Settings&quot; dialog. |
| [OpenStatistics](-open-statistics/index.html) | [jvm]<br>object [OpenStatistics](-open-statistics/index.html) : [RootComponent.Intent](index.html)<br>Open the &quot;Statistics&quot; dialog. |
| [SaveCurrentProject](-save-current-project/index.html) | [jvm]<br>object [SaveCurrentProject](-save-current-project/index.html) : [RootComponent.Intent](index.html)<br>Save the current project in the TMX at the specified path. |
| [SaveProject](-save-project/index.html) | [jvm]<br>data class [SaveProject](-save-project/index.html)(val path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.Intent](index.html)<br>Save the project as a TMX file at the given path. |
| [SaveProjectAs](-save-project-as/index.html) | [jvm]<br>object [SaveProjectAs](-save-project-as/index.html) : [RootComponent.Intent](index.html)<br>Opens the &quot;Save as&quot; project dialog. |
| [SyncTm](-sync-tm/index.html) | [jvm]<br>object [SyncTm](-sync-tm/index.html) : [RootComponent.Intent](index.html)<br>Import all the messages of the current project into the global TM. |
| [ValidatePlaceholders](-validate-placeholders/index.html) | [jvm]<br>object [ValidatePlaceholders](-validate-placeholders/index.html) : [RootComponent.Intent](index.html)<br>Starts the placeholder validation. |

