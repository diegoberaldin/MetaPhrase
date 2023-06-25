---
title: ProjectRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.repository](../index.html)/[ProjectRepository](index.html)



# ProjectRepository



[jvm]\
interface [ProjectRepository](index.html)

Contract for the project repository.



## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a project in the DB. |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Delete a project. |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)()<br>Delete all projects. |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt;<br>Get all projects. |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?<br>Get a project by id. |
| [isNeedsSaving](is-needs-saving.html) | [jvm]<br>abstract fun [isNeedsSaving](is-needs-saving.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Scalar value of the flag indicating there are unsaved changes. |
| [observeById](observe-by-id.html) | [jvm]<br>abstract fun [observeById](observe-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt;<br>Observe a project given its ID. |
| [observeNeedsSaving](observe-needs-saving.html) | [jvm]<br>abstract fun [observeNeedsSaving](observe-needs-saving.html)(): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt;<br>Observe the value of the flag indicating there are unsaved changes. |
| [setNeedsSaving](set-needs-saving.html) | [jvm]<br>abstract fun [setNeedsSaving](set-needs-saving.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>Set the flag indicating there are unsaved changes. |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Update an existing project. |

