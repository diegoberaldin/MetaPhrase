---
title: TranslateComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.translate.presentation](../index.html)/[TranslateComponent](index.html)



# TranslateComponent



[jvm]\
interface [TranslateComponent](index.html)

Translate component.



## Types


| Name | Summary |
|---|---|
| [DialogConfig](-dialog-config/index.html) | [jvm]<br>interface [DialogConfig](-dialog-config/index.html) : Parcelable<br>Available dialog configurations for the [dialog](dialog.html) slot. |
| [MessageListConfig](-message-list-config/index.html) | [jvm]<br>object [MessageListConfig](-message-list-config/index.html) : Parcelable<br>Slot configuration for the message list. This is the only config value available in the [messageList](message-list.html) slot. |
| [PanelConfig](-panel-config/index.html) | [jvm]<br>interface [PanelConfig](-panel-config/index.html) : Parcelable<br>Available configurations for the [panel](panel.html) slot. |
| [ToolbarConfig](-toolbar-config/index.html) | [jvm]<br>object [ToolbarConfig](-toolbar-config/index.html) : Parcelable<br>Slot configuration for the translation toolbar. This is the only config value available in the [toolbar](toolbar.html) slot. |


## Properties


| Name | Summary |
|---|---|
| [currentLanguage](current-language.html) | [jvm]<br>abstract val [currentLanguage](current-language.html): StateFlow&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?&gt;<br>Language selected in the translation toolbar |
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[TranslateComponent.DialogConfig](-dialog-config/index.html), *&gt;&gt;<br>Navigation slot for the dialogs |
| [isEditing](is-editing.html) | [jvm]<br>abstract val [isEditing](is-editing.html): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>A flag indicating whether any message is opened for editing |
| [messageList](message-list.html) | [jvm]<br>abstract val [messageList](message-list.html): Value&lt;ChildSlot&lt;[TranslateComponent.MessageListConfig](-message-list-config/index.html), [MessageListComponent](../../com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation/-message-list-component/index.html)&gt;&gt;<br>Navigation slot for the message list |
| [panel](panel.html) | [jvm]<br>abstract val [panel](panel.html): Value&lt;ChildSlot&lt;[TranslateComponent.PanelConfig](-panel-config/index.html), *&gt;&gt;<br>Navigation slot for the bottom panel |
| [projectId](project-id.html) | [jvm]<br>abstract var [projectId](project-id.html): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Current project ID |
| [toolbar](toolbar.html) | [jvm]<br>abstract val [toolbar](toolbar.html): Value&lt;ChildSlot&lt;[TranslateComponent.ToolbarConfig](-toolbar-config/index.html), [TranslateToolbarComponent](../../com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation/-translate-toolbar-component/index.html)&gt;&gt;<br>Navigation slot for the toolbar |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[TranslateUiState](../-translate-ui-state/index.html)&gt;<br>UI state |


## Functions


| Name | Summary |
|---|---|
| [addGlossaryTerm](add-glossary-term.html) | [jvm]<br>abstract fun [addGlossaryTerm](add-glossary-term.html)(source: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?, target: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)<br>Add a new glossary term. |
| [addSegment](add-segment.html) | [jvm]<br>abstract fun [addSegment](add-segment.html)()<br>Add a new segment (opens dialog). |
| [closeDialog](close-dialog.html) | [jvm]<br>abstract fun [closeDialog](close-dialog.html)()<br>Close any opened dialog. |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)()<br>Copy the base (source) message to the translation field in the editor. |
| [deleteSegment](delete-segment.html) | [jvm]<br>abstract fun [deleteSegment](delete-segment.html)()<br>Delete the current segment. |
| [endEditing](end-editing.html) | [jvm]<br>abstract fun [endEditing](end-editing.html)()<br>End the current editing operation. |
| [export](export.html) | [jvm]<br>abstract fun [export](export.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Export the current language messages to a resource file. |
| [exportTmx](export-tmx.html) | [jvm]<br>abstract fun [exportTmx](export-tmx.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Export the global TM as a TMX file. |
| [globalSpellcheck](global-spellcheck.html) | [jvm]<br>abstract fun [globalSpellcheck](global-spellcheck.html)()<br>Start a global spellcheck validation. |
| [import](import.html) | [jvm]<br>abstract fun [import](import.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Import a list of message for the current language from a resource file. |
| [insertBestMatch](insert-best-match.html) | [jvm]<br>abstract fun [insertBestMatch](insert-best-match.html)()<br>Insert the best match from translation memory in the translation field of the editor. |
| [machineTranslationContributeTm](machine-translation-contribute-tm.html) | [jvm]<br>abstract fun [machineTranslationContributeTm](machine-translation-contribute-tm.html)()<br>Share the whole project content (all messages for all languages) with the machine translation provider. |
| [machineTranslationCopyTarget](machine-translation-copy-target.html) | [jvm]<br>abstract fun [machineTranslationCopyTarget](machine-translation-copy-target.html)()<br>Copy the current translation of the editor into the MT suggestion. |
| [machineTranslationInsert](machine-translation-insert.html) | [jvm]<br>abstract fun [machineTranslationInsert](machine-translation-insert.html)()<br>Copy the MT suggestion into the translation editor. |
| [machineTranslationRetrieve](machine-translation-retrieve.html) | [jvm]<br>abstract fun [machineTranslationRetrieve](machine-translation-retrieve.html)()<br>Retrieve a suggestion for the current message from the machine translation provider. In order for this to wark, the [tryLoadMachineTranslation](try-load-machine-translation.html) method should have been called earlier. |
| [machineTranslationShare](machine-translation-share.html) | [jvm]<br>abstract fun [machineTranslationShare](machine-translation-share.html)()<br>Share the current suggestion (after editing) with the machine translation provider. |
| [moveToNext](move-to-next.html) | [jvm]<br>abstract fun [moveToNext](move-to-next.html)()<br>Moves the cursor to the next message. |
| [moveToPrevious](move-to-previous.html) | [jvm]<br>abstract fun [moveToPrevious](move-to-previous.html)()<br>Moves the cursor to the previous message. |
| [save](save.html) | [jvm]<br>abstract fun [save](save.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Save the current project to a TMX file. |
| [syncWithTm](sync-with-tm.html) | [jvm]<br>abstract fun [syncWithTm](sync-with-tm.html)()<br>Export all the segments of the current project to the global translation memory. |
| [togglePanel](toggle-panel.html) | [jvm]<br>abstract fun [togglePanel](toggle-panel.html)(config: [TranslateComponent.PanelConfig](-panel-config/index.html))<br>Toggle a bottom panel for a given section) |
| [tryLoadGlossary](try-load-glossary.html) | [jvm]<br>abstract fun [tryLoadGlossary](try-load-glossary.html)()<br>Try and load terms from the glossary for the currently opened message. |
| [tryLoadMachineTranslation](try-load-machine-translation.html) | [jvm]<br>abstract fun [tryLoadMachineTranslation](try-load-machine-translation.html)()<br>Loads the source version of the current message in machine translation component. (This does not retrieve any result from the MT provider, use [machineTranslationRetrieve](machine-translation-retrieve.html) after having called this method) |
| [tryLoadSimilarities](try-load-similarities.html) | [jvm]<br>abstract fun [tryLoadSimilarities](try-load-similarities.html)()<br>Try and load similarities from the translation memory for the currently opened message. |
| [validatePlaceholders](validate-placeholders.html) | [jvm]<br>abstract fun [validatePlaceholders](validate-placeholders.html)()<br>Start a placeholder validation. |

