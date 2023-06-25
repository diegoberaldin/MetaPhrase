---
title: RecentProjectDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[RecentProjectDao](index.html)



# RecentProjectDao

interface [RecentProjectDao](index.html)

Contract for the recent project data access object.



#### Inheritors


| |
|---|
| [DefaultRecentProjectDao](../-default-recent-project-dao/index.html) |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a recent project. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html))<br>Delete a recent project. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)&gt;<br>Get all recent projects. |
| [getByName](get-by-name.html) | [jvm]<br>abstract suspend fun [getByName](get-by-name.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)?<br>Get a recent project by name. |

