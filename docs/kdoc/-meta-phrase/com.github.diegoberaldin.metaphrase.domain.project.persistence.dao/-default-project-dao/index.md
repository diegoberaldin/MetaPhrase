---
title: DefaultProjectDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[DefaultProjectDao](index.html)



# DefaultProjectDao



[jvm]\
class [DefaultProjectDao](index.html) : [ProjectDao](../-project-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultProjectDao](-default-project-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Create a new project. |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Delete a project. |
| [deleteAll](delete-all.html) | [jvm]<br>open suspend override fun [deleteAll](delete-all.html)()<br>Delete all projects. |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt;<br>Get all projects in the DB. |
| [getById](get-by-id.html) | [jvm]<br>open suspend override fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)?<br>Get a project by ID. |
| [update](update.html) | [jvm]<br>open suspend override fun [update](update.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html))<br>Update a project. |

