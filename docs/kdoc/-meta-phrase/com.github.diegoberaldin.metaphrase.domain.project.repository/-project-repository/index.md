---
title: ProjectRepository
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.repository](../index.html)/[ProjectRepository](index.html)



# ProjectRepository



[jvm]\
interface [ProjectRepository](index.html)



## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>abstract suspend fun [create](create.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [delete](delete.html) | [jvm]<br>abstract suspend fun [delete](delete.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)) |
| [deleteAll](delete-all.html) | [jvm]<br>abstract suspend fun [deleteAll](delete-all.html)() |
| [getAll](get-all.html) | [jvm]<br>abstract suspend fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt; |
| [getById](get-by-id.html) | [jvm]<br>abstract suspend fun [getById](get-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)? |
| [isNeedsSaving](is-needs-saving.html) | [jvm]<br>abstract fun [isNeedsSaving](is-needs-saving.html)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [observeById](observe-by-id.html) | [jvm]<br>abstract fun [observeById](observe-by-id.html)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): Flow&lt;[ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)&gt; |
| [observeNeedsSaving](observe-needs-saving.html) | [jvm]<br>abstract fun [observeNeedsSaving](observe-needs-saving.html)(): Flow&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)&gt; |
| [setNeedsSaving](set-needs-saving.html) | [jvm]<br>abstract fun [setNeedsSaving](set-needs-saving.html)(value: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [update](update.html) | [jvm]<br>abstract suspend fun [update](update.html)(model: [ProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-project-model/index.html)) |

