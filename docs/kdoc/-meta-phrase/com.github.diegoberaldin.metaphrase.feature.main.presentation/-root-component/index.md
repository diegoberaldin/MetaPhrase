---
title: RootComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../index.html)/[RootComponent](index.html)



# RootComponent



[jvm]\
interface [RootComponent](index.html)

Root component.



## Types


| Name | Summary |
|---|---|
| [Config](-config/index.html) | [jvm]<br>interface [Config](-config/index.html) : Parcelable<br>Main screen content slot configuration. |
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available dialog configurations. |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[RootComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Navigation slot for currently opened dialog. |
| [main](main.html) | [jvm]<br>abstract val [main](main.html): Value&lt;ChildSlot&lt;[RootComponent.Config](-config/index.html), *&gt;&gt;<br>Navigation slot for the main content. |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[RootUiState](../-root-ui-state/index.html)&gt;<br>UI state. |


## Functions


| Name | Summary |
|---|---|
| [addSegment](add-segment.html) | [jvm]<br>abstract fun [addSegment](add-segment.html)()<br>Open the &quot;New segment&quot; dialog. |
| [clearGlossary](clear-glossary.html) | [jvm]<br>abstract fun [clearGlossary](clear-glossary.html)()<br>Clear the global glossary. |
| [clearTm](clear-tm.html) | [jvm]<br>abstract fun [clearTm](clear-tm.html)()<br>Clear the content of the global Translation Memory. |
| [closeCurrentProject](close-current-project.html) | [jvm]<br>abstract fun [closeCurrentProject](close-current-project.html)(closeAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Displays the &quot;Confirm project close&quot; dialog. |
| [closeDialog](close-dialog.html) | [jvm]<br>abstract fun [closeDialog](close-dialog.html)()<br>Close any opened dialog. |
| [confirmCloseCurrentProject](confirm-close-current-project.html) | [jvm]<br>abstract fun [confirmCloseCurrentProject](confirm-close-current-project.html)(openAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, newAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)<br>Sends user confirmation to close the current project with an optional action afterwards. |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)()<br>Copy the base (source) message to the target message. |
| [deleteSegment](delete-segment.html) | [jvm]<br>abstract fun [deleteSegment](delete-segment.html)()<br>Delete the current segment. |
| [endEditing](end-editing.html) | [jvm]<br>abstract fun [endEditing](end-editing.html)()<br>End editing the current message. |
| [export](export.html) | [jvm]<br>abstract fun [export](export.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Export messages to a resource file. |
| [exportGlossary](export-glossary.html) | [jvm]<br>abstract fun [exportGlossary](export-glossary.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Export the global glossary to a CSV file. |
| [exportTmx](export-tmx.html) | [jvm]<br>abstract fun [exportTmx](export-tmx.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Export the current TM content to a TMX file. |
| [globalSpellcheck](global-spellcheck.html) | [jvm]<br>abstract fun [globalSpellcheck](global-spellcheck.html)()<br>Start a global spellcheck validation. |
| [hasUnsavedChanges](has-unsaved-changes.html) | [jvm]<br>abstract fun [hasUnsavedChanges](has-unsaved-changes.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Returns whether the current project has any unsaved changes. |
| [import](import.html) | [jvm]<br>abstract fun [import](import.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Import messages from a resource file. |
| [importGlossary](import-glossary.html) | [jvm]<br>abstract fun [importGlossary](import-glossary.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Import a CSV file into the glossary. |
| [importTmx](import-tmx.html) | [jvm]<br>abstract fun [importTmx](import-tmx.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Import a TMX file to the translation memory. |
| [insertBestMatch](insert-best-match.html) | [jvm]<br>abstract fun [insertBestMatch](insert-best-match.html)()<br>Insert the best TM match to the translation editor. |
| [machineTranslationContributeTm](machine-translation-contribute-tm.html) | [jvm]<br>abstract fun [machineTranslationContributeTm](machine-translation-contribute-tm.html)()<br>Share the whole project as a TM to the machine translation provider. |
| [machineTranslationCopyTarget](machine-translation-copy-target.html) | [jvm]<br>abstract fun [machineTranslationCopyTarget](machine-translation-copy-target.html)()<br>Copy the target message into the machine translation suggestion. |
| [machineTranslationInsert](machine-translation-insert.html) | [jvm]<br>abstract fun [machineTranslationInsert](machine-translation-insert.html)()<br>Insert the current suggestion from machine translation in the editor. |
| [machineTranslationRetrieve](machine-translation-retrieve.html) | [jvm]<br>abstract fun [machineTranslationRetrieve](machine-translation-retrieve.html)()<br>Retrieve a suggestion for the current message from the machine translation provider. |
| [machineTranslationShare](machine-translation-share.html) | [jvm]<br>abstract fun [machineTranslationShare](machine-translation-share.html)()<br>Share the current suggestion to the remote machine translation provider. |
| [moveToNextSegment](move-to-next-segment.html) | [jvm]<br>abstract fun [moveToNextSegment](move-to-next-segment.html)()<br>Navigate to the next segment in the editor. |
| [moveToPreviousSegment](move-to-previous-segment.html) | [jvm]<br>abstract fun [moveToPreviousSegment](move-to-previous-segment.html)()<br>Navigate to the previous segment in the editor. |
| [openDialog](open-dialog.html) | [jvm]<br>abstract fun [openDialog](open-dialog.html)()<br>Open the &quot;Open pooject&quot; dialog. |
| [openEditProject](open-edit-project.html) | [jvm]<br>abstract fun [openEditProject](open-edit-project.html)()<br>Open the &quot;Edit project&quot; dialog. |
| [openExportDialog](open-export-dialog.html) | [jvm]<br>abstract fun [openExportDialog](open-export-dialog.html)(type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Open the export dialog for the current language. |
| [openExportGlossaryDialog](open-export-glossary-dialog.html) | [jvm]<br>abstract fun [openExportGlossaryDialog](open-export-glossary-dialog.html)()<br>Open the export glossary dialog. |
| [openExportTmxDialog](open-export-tmx-dialog.html) | [jvm]<br>abstract fun [openExportTmxDialog](open-export-tmx-dialog.html)()<br>Open the dialog to export the current TM content to TMX. |
| [openImportDialog](open-import-dialog.html) | [jvm]<br>abstract fun [openImportDialog](open-import-dialog.html)(type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Open the import dialog for the current language. |
| [openImportGlossaryDialog](open-import-glossary-dialog.html) | [jvm]<br>abstract fun [openImportGlossaryDialog](open-import-glossary-dialog.html)()<br>Open the import glossary dialog. |
| [openImportTmxDialog](open-import-tmx-dialog.html) | [jvm]<br>abstract fun [openImportTmxDialog](open-import-tmx-dialog.html)()<br>Open the import dialog to populate the TM. |
| [openManual](open-manual.html) | [jvm]<br>abstract fun [openManual](open-manual.html)()<br>Open the user manual. |
| [openNewDialog](open-new-dialog.html) | [jvm]<br>abstract fun [openNewDialog](open-new-dialog.html)()<br>Open the &quot;New project&quot; dialog. |
| [openProject](open-project.html) | [jvm]<br>abstract fun [openProject](open-project.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Loads a translation project from disk. |
| [openSettings](open-settings.html) | [jvm]<br>abstract fun [openSettings](open-settings.html)()<br>Open the &quot;Settings&quot; dialog. |
| [openStatistics](open-statistics.html) | [jvm]<br>abstract fun [openStatistics](open-statistics.html)()<br>Open the &quot;Statistics&quot; dialog. |
| [saveCurrentProject](save-current-project.html) | [jvm]<br>abstract fun [saveCurrentProject](save-current-project.html)()<br>Save the current project in the TMX at the specified path. |
| [saveProject](save-project.html) | [jvm]<br>abstract fun [saveProject](save-project.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Save the project as a TMX file at the given path. |
| [saveProjectAs](save-project-as.html) | [jvm]<br>abstract fun [saveProjectAs](save-project-as.html)()<br>Opens the &quot;Save as&quot; project dialog. |
| [syncTm](sync-tm.html) | [jvm]<br>abstract fun [syncTm](sync-tm.html)()<br>Import all the messages of the current project into the global TM. |
| [validatePlaceholders](validate-placeholders.html) | [jvm]<br>abstract fun [validatePlaceholders](validate-placeholders.html)()<br>Starts the placeholder validation. |

