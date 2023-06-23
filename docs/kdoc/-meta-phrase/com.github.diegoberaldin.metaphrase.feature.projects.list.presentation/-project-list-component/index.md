---
title: ProjectListComponent
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.list.presentation](../index.html)/[ProjectListComponent](index.html)



# ProjectListComponent



[jvm]\
interface [ProjectListComponent](index.html)



## Types


| Name | Summary |
|---|---|
| [DialogConfiguration](-dialog-configuration/index.html) | [jvm]<br>interface [DialogConfiguration](-dialog-configuration/index.html) : Parcelable |


## Properties


| Name | Summary |
|---|---|
| [dialog](dialog.html) | [jvm]<br>abstract val [dialog](dialog.html): Value&lt;ChildSlot&lt;[ProjectListComponent.DialogConfiguration](-dialog-configuration/index.html), *&gt;&gt; |
| [projectSelected](project-selected.html) | [jvm]<br>abstract val [projectSelected](project-selected.html): SharedFlow&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt; |
| [uiState](ui-state.html) | [jvm]<br>abstract val [uiState](ui-state.html): StateFlow&lt;[ProjectListUiState](../-project-list-ui-state/index.html)&gt; |


## Functions


| Name | Summary |
|---|---|
| [closeDialog](close-dialog.html) | [jvm]<br>abstract fun [closeDialog](close-dialog.html)() |
| [openRecent](open-recent.html) | [jvm]<br>abstract fun [openRecent](open-recent.html)(value: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)) |
| [removeFromRecent](remove-from-recent.html) | [jvm]<br>abstract fun [removeFromRecent](remove-from-recent.html)(value: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)) |

