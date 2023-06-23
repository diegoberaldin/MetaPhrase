---
title: DefaultRecentProjectDao
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.domain.project.persistence.dao](../index.html)/[DefaultRecentProjectDao](index.html)



# DefaultRecentProjectDao



[jvm]\
class [DefaultRecentProjectDao](index.html) : [RecentProjectDao](../-recent-project-dao/index.html)



## Constructors


| | |
|---|---|
| [DefaultRecentProjectDao](-default-recent-project-dao.html) | [jvm]<br>constructor() |


## Functions


| Name | Summary |
|---|---|
| [create](create.html) | [jvm]<br>open suspend override fun [create](create.html)(model: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [delete](delete.html) | [jvm]<br>open suspend override fun [delete](delete.html)(model: [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)) |
| [getAll](get-all.html) | [jvm]<br>open suspend override fun [getAll](get-all.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)&gt; |
| [getByName](get-by-name.html) | [jvm]<br>open suspend override fun [getByName](get-by-name.html)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [RecentProjectModel](../../com.github.diegoberaldin.metaphrase.domain.project.data/-recent-project-model/index.html)? |

