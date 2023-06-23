---
title: DialogConfig
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.main.presentation](../../index.html)/[RootComponent](../index.html)/[DialogConfig](index.html)



# DialogConfig

interface [DialogConfig](index.html) : Parcelable

Available dialog configurations.



#### Inheritors


| |
|---|
| [None](-none/index.html) |
| [OpenDialog](-open-dialog/index.html) |
| [NewDialog](-new-dialog/index.html) |
| [EditDialog](-edit-dialog/index.html) |
| [SaveAsDialog](-save-as-dialog/index.html) |
| [ConfirmCloseDialog](-confirm-close-dialog/index.html) |
| [ImportDialog](-import-dialog/index.html) |
| [ExportDialog](-export-dialog/index.html) |
| [StatisticsDialog](-statistics-dialog/index.html) |
| [SettingsDialog](-settings-dialog/index.html) |
| [ExportTmxDialog](-export-tmx-dialog/index.html) |
| [ImportGlossaryDialog](-import-glossary-dialog/index.html) |
| [ImportTmxDialog](-import-tmx-dialog/index.html) |
| [ExportGlossaryDialog](-export-glossary-dialog/index.html) |


## Types


| Name | Summary |
|---|---|
| [ConfirmCloseDialog](-confirm-close-dialog/index.html) | [jvm]<br>data class [ConfirmCloseDialog](-confirm-close-dialog/index.html)(val closeAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val openAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, val newAfter: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) : [RootComponent.DialogConfig](index.html)<br>Confirm close dialog if there are unsaved changes before closing the application or opening a new project. |
| [EditDialog](-edit-dialog/index.html) | [jvm]<br>object [EditDialog](-edit-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Project edit dialog |
| [ExportDialog](-export-dialog/index.html) | [jvm]<br>data class [ExportDialog](-export-dialog/index.html)(val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.DialogConfig](index.html)<br>Export messages dialog. |
| [ExportGlossaryDialog](-export-glossary-dialog/index.html) | [jvm]<br>object [ExportGlossaryDialog](-export-glossary-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Export glossary dialog. |
| [ExportTmxDialog](-export-tmx-dialog/index.html) | [jvm]<br>object [ExportTmxDialog](-export-tmx-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Export TM dialog. |
| [ImportDialog](-import-dialog/index.html) | [jvm]<br>data class [ImportDialog](-import-dialog/index.html)(val type: [ResourceFileType](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-resource-file-type/index.html)) : [RootComponent.DialogConfig](index.html)<br>Import messages dialog. |
| [ImportGlossaryDialog](-import-glossary-dialog/index.html) | [jvm]<br>object [ImportGlossaryDialog](-import-glossary-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Import glossary dialog. |
| [ImportTmxDialog](-import-tmx-dialog/index.html) | [jvm]<br>object [ImportTmxDialog](-import-tmx-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Import TM dialog. |
| [NewDialog](-new-dialog/index.html) | [jvm]<br>object [NewDialog](-new-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>New project dialog |
| [None](-none/index.html) | [jvm]<br>object [None](-none/index.html) : [RootComponent.DialogConfig](index.html)<br>No dialog (close dialog) |
| [OpenDialog](-open-dialog/index.html) | [jvm]<br>object [OpenDialog](-open-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Open project dialog |
| [SaveAsDialog](-save-as-dialog/index.html) | [jvm]<br>data class [SaveAsDialog](-save-as-dialog/index.html)(val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [RootComponent.DialogConfig](index.html)<br>Save project dialog (aka &quot;Save as&quot;). |
| [SettingsDialog](-settings-dialog/index.html) | [jvm]<br>object [SettingsDialog](-settings-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Settings dialog. |
| [StatisticsDialog](-statistics-dialog/index.html) | [jvm]<br>object [StatisticsDialog](-statistics-dialog/index.html) : [RootComponent.DialogConfig](index.html)<br>Statistics dialog. |

