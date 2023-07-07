---
title: Intent
---
//[MetaPhrase](../../../../index.html)/[com.github.diegoberaldin.metaphrase.feature.projects.list.presentation](../../index.html)/[ProjectListComponent](../index.html)/[Intent](index.html)



# Intent

interface [Intent](index.html)

View intents.



#### Inheritors


| |
|---|
| [OpenRecent](-open-recent/index.html) |
| [RemoveFromRecent](-remove-from-recent/index.html) |
| [CloseDialog](-close-dialog/index.html) |


## Types


| Name | Summary |
|---|---|
| [CloseDialog](-close-dialog/index.html) | [jvm]<br>object [CloseDialog](-close-dialog/index.html) : [ProjectListComponent.Intent](index.html)<br>Close the currently opened dialog. |
| [OpenRecent](-open-recent/index.html) | [jvm]<br>data class [OpenRecent](-open-recent/index.html)(val value: [RecentProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)) : [ProjectListComponent.Intent](index.html)<br>Open a recent project. |
| [RemoveFromRecent](-remove-from-recent/index.html) | [jvm]<br>data class [RemoveFromRecent](-remove-from-recent/index.html)(val value: [RecentProjectModel](../../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)) : [ProjectListComponent.Intent](index.html)<br>Remove an item from the recent project list . |

