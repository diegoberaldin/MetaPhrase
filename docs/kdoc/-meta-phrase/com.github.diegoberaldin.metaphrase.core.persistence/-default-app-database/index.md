---
title: DefaultAppDatabase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.persistence](../index.html)/[DefaultAppDatabase](index.html)



# DefaultAppDatabase



[jvm]\
class [DefaultAppDatabase](index.html)(filename: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = FILE_NAME, fileManager: [FileManager](../../com.github.diegoberaldin.metaphrase.core.common.files/-file-manager/index.html)) : [AppDatabase](../-app-database/index.html)

Default app database implementation that uses a local H2 DB instance on the local filesystem.



## Constructors


| | |
|---|---|
| [DefaultAppDatabase](-default-app-database.html) | [jvm]<br>constructor(filename: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = FILE_NAME, fileManager: [FileManager](../../com.github.diegoberaldin.metaphrase.core.common.files/-file-manager/index.html))<br>Create [DefaultAppDatabase](index.html) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [glossaryTermDao](glossary-term-dao.html) | [jvm]<br>open override fun [glossaryTermDao](glossary-term-dao.html)(): [DefaultGlossaryTermDao](../../com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao/-default-glossary-term-dao/index.html)<br>Create a new glossary term DAO. |
| [languageDao](language-dao.html) | [jvm]<br>open override fun [languageDao](language-dao.html)(): [DefaultLanguageDao](../../com.github.diegoberaldin.metaphrase.domain.language.persistence.dao/-default-language-dao/index.html)<br>Create a new language DAO. |
| [memoryEntryDao](memory-entry-dao.html) | [jvm]<br>open override fun [memoryEntryDao](memory-entry-dao.html)(): [DefaultMemoryEntryDao](../../com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao/-default-memory-entry-dao/index.html)<br>Create a new memory entry DAO. |
| [projectDao](project-dao.html) | [jvm]<br>open override fun [projectDao](project-dao.html)(): [DefaultProjectDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-default-project-dao/index.html)<br>Create a new project DAO. |
| [recentProjectDao](recent-project-dao.html) | [jvm]<br>open override fun [recentProjectDao](recent-project-dao.html)(): [DefaultRecentProjectDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-default-recent-project-dao/index.html)<br>Create a new recent project DAO. |
| [segmentDao](segment-dao.html) | [jvm]<br>open override fun [segmentDao](segment-dao.html)(): [DefaultSegmentDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-default-segment-dao/index.html)<br>Create a new segment DAO. |

