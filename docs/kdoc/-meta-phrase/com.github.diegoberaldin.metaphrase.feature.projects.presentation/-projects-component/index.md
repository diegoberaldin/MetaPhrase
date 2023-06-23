---
title: ProjectsComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.presentation](../index.html)/[ProjectsComponent](index.html)



# ProjectsComponent



[jvm]\
interface [ProjectsComponent](index.html)

Projects component.



## Types


| Name | Summary |
|---|---|
| [Config](-config/index.html) | [jvm]<br>interface [Config](-config/index.html) : Parcelable<br>Available screen configuration. |


## Properties


| Name | Summary |
|---|---|
| [activeProject](active-project.html) | [jvm]<br>abstract val [activeProject](active-project.html): StateFlow&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?&gt;<br>Currently active (opened) project. |
| [childStack](child-stack.html) | [jvm]<br>abstract val [childStack](child-stack.html): Value&lt;ChildStack&lt;[ProjectsComponent.Config](-config/index.html), *&gt;&gt;<br>Navigation stack for the project list/detail. |
| [currentLanguage](current-language.html) | [jvm]<br>abstract val [currentLanguage](current-language.html): StateFlow&lt;[LanguageModel](../../com.github.diegoberaldin.metaphrase.domain.language.data/-language-model/index.html)?&gt;<br>Currently selected language in the translate toolbar (if any). |
| [isEditing](is-editing.html) | [jvm]<br>abstract val [isEditing](is-editing.html): StateFlow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>True if a message (Translation Unit) is being edited. |


## Functions


| Name | Summary |
|---|---|
| [addSegment](add-segment.html) | [jvm]<br>abstract fun [addSegment](add-segment.html)()<br>Add a new segment (trigger dialog) |
| [closeCurrentProject](close-current-project.html) | [jvm]<br>abstract fun [closeCurrentProject](close-current-project.html)()<br>Close the current project. |
| [copyBase](copy-base.html) | [jvm]<br>abstract fun [copyBase](copy-base.html)()<br>Copy the base (source) message to the target editor field. |
| [deleteSegment](delete-segment.html) | [jvm]<br>abstract fun [deleteSegment](delete-segment.html)()<br>Delete the current segment. |
| [endEditing](end-editing.html) | [jvm]<br>abstract fun [endEditing](end-editing.html)()<br>Close the current editing operation. |
| [export](export.html) | [jvm]<br>abstract fun [export](export.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Export the messages of the current language to a resource file. |
| [exportTmx](export-tmx.html) | [jvm]<br>abstract fun [exportTmx](export-tmx.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Export the TM content to a  TMX file. |
| [globalSpellcheck](global-spellcheck.html) | [jvm]<br>abstract fun [globalSpellcheck](global-spellcheck.html)()<br>Starts a global spellcheck validation. |
| [import](import.html) | [jvm]<br>abstract fun [import](import.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), type: [ResourceFileType](../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html))<br>Import messages for the current language from a resource file. |
| [insertBestMatch](insert-best-match.html) | [jvm]<br>abstract fun [insertBestMatch](insert-best-match.html)()<br>Insert the best TM match into the translation editor. |
| [machineTranslationContributeTm](machine-translation-contribute-tm.html) | [jvm]<br>abstract fun [machineTranslationContributeTm](machine-translation-contribute-tm.html)()<br>Share the whole content of the project with the TM provider. |
| [machineTranslationCopyTarget](machine-translation-copy-target.html) | [jvm]<br>abstract fun [machineTranslationCopyTarget](machine-translation-copy-target.html)()<br>Copy the target message in the editor to the TM suggestion. |
| [machineTranslationInsert](machine-translation-insert.html) | [jvm]<br>abstract fun [machineTranslationInsert](machine-translation-insert.html)()<br>Insert the MT suggestion in the translation editor field. |
| [machineTranslationRetrieve](machine-translation-retrieve.html) | [jvm]<br>abstract fun [machineTranslationRetrieve](machine-translation-retrieve.html)()<br>Retrieve a suggestion from the MT provider. |
| [machineTranslationShare](machine-translation-share.html) | [jvm]<br>abstract fun [machineTranslationShare](machine-translation-share.html)()<br>Share the current suggestion to the TM provider. |
| [moveToNext](move-to-next.html) | [jvm]<br>abstract fun [moveToNext](move-to-next.html)()<br>Move the cursor to the next message. |
| [moveToPrevious](move-to-previous.html) | [jvm]<br>abstract fun [moveToPrevious](move-to-previous.html)()<br>Move the cursor to the previous message. |
| [open](open.html) | [jvm]<br>abstract fun [open](open.html)(projectId: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Open a project with a given ID. |
| [saveCurrentProject](save-current-project.html) | [jvm]<br>abstract fun [saveCurrentProject](save-current-project.html)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))<br>Save the current project. |
| [syncWithTm](sync-with-tm.html) | [jvm]<br>abstract fun [syncWithTm](sync-with-tm.html)()<br>Saves all the messages of the current project in the global TM. |
| [validatePlaceholders](validate-placeholders.html) | [jvm]<br>abstract fun [validatePlaceholders](validate-placeholders.html)()<br>Start a placeholder validation. |

