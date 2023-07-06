package com.github.diegoberaldin.metaphrase.core.persistence

import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.GlossaryTermDao
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.LanguageDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.ProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.RecentProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.SegmentDao
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.MemoryEntryDao

/**
 * Database abstraction that contains the Data Access Object (DAO) factory methods.
 */
interface AppDatabase {
    /**
     * Create a new project DAO.
     *
     * @return [ProjectDao]
     */
    fun projectDao(): ProjectDao

    /**
     * Create a new language DAO.
     *
     * @return [LanguageDao]
     */
    fun languageDao(): LanguageDao

    /**
     * Create a new segment DAO.
     *
     * @return [SegmentDao]
     */
    fun segmentDao(): SegmentDao

    /**
     * Create a new memory entry DAO.
     *
     * @return [MemoryEntryDao]
     */
    fun memoryEntryDao(): MemoryEntryDao

    /**
     * Create a new glossary term DAO.
     *
     * @return [GlossaryTermDao]
     */
    fun glossaryTermDao(): GlossaryTermDao

    /**
     * Create a new recent project DAO.
     *
     * @return [RecentProjectDao]
     */
    fun recentProjectDao(): RecentProjectDao
}
