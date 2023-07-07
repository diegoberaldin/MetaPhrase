---
title: AppDatabase
---
//[MetaPhrase](../../../index.html)/[com.github.diegoberaldin.metaphrase.core.persistence](../index.html)/[AppDatabase](index.html)



# AppDatabase

interface [AppDatabase](index.html)

Database abstraction that contains the Data Access Object (DAO) factory methods.



#### Inheritors


| |
|---|
| [DefaultAppDatabase](../-default-app-database/index.html) |


## Functions


| Name | Summary |
|---|---|
| [glossaryTermDao](glossary-term-dao.html) | [jvm]<br>abstract fun [glossaryTermDao](glossary-term-dao.html)(): [GlossaryTermDao](../../com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao/-glossary-term-dao/index.html)<br>Create a new glossary term DAO. |
| [languageDao](language-dao.html) | [jvm]<br>abstract fun [languageDao](language-dao.html)(): [LanguageDao](../../com.github.diegoberaldin.metaphrase.domain.language.persistence.dao/-language-dao/index.html)<br>Create a new language DAO. |
| [memoryEntryDao](memory-entry-dao.html) | [jvm]<br>abstract fun [memoryEntryDao](memory-entry-dao.html)(): [MemoryEntryDao](../../com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao/-memory-entry-dao/index.html)<br>Create a new memory entry DAO. |
| [projectDao](project-dao.html) | [jvm]<br>abstract fun [projectDao](project-dao.html)(): [ProjectDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-project-dao/index.html)<br>Create a new project DAO. |
| [recentProjectDao](recent-project-dao.html) | [jvm]<br>abstract fun [recentProjectDao](recent-project-dao.html)(): [RecentProjectDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-recent-project-dao/index.html)<br>Create a new recent project DAO. |
| [segmentDao](segment-dao.html) | [jvm]<br>abstract fun [segmentDao](segment-dao.html)(): [SegmentDao](../../com.github.diegoberaldin.metaphrase.domain.project.persistence.dao/-segment-dao/index.html)<br>Create a new segment DAO. |

