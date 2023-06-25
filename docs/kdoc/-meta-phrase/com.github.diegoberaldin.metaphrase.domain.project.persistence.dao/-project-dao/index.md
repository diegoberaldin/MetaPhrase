---
title: ProjectDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[ProjectDao](index.html)



# ProjectDao

interface [ProjectDao](index.html)

Contract for the project data access object.



#### Inheritors


| |
|---|
| [DefaultProjectDao](../-default-project-dao/index.html) |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new project. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Delete a project. |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)()<br>Delete all projects. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt;<br>Get all projects in the DB. |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?<br>Get a project by ID. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Update a project. |

