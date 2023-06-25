package com.github.diegoberaldin.metaphrase.core.persistence

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.DefaultGlossaryTermDao
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities.GlossaryTermEntity
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities.GlossaryTermRelationshipEntity
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.DefaultLanguageDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultRecentProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultSegmentDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.LanguageEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.ProjectEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.RecentProjectEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.SegmentEntity
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.DefaultMemoryEntryDao
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryEntryEntity
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryMessageEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Application DB that contains shared logic to access the embedded DB on local files as well as the DAO factory methods.
 *
 * @property filename Name of the file to read/write to
 * @property fileManager Utility to access files on disk
 * @constructor Create [AppDatabase]
 */
class AppDatabase(
    private val filename: String = FILE_NAME,
    private val fileManager: FileManager,
) {
    companion object {
        private const val DRIVER = "org.h2.Driver"
        private const val PROTO = "h2:file"
        private const val EXTRA_PARAMS = ";MODE=MYSQL"
        private const val FILE_NAME = "main"
    }

    init {
        setup()
    }

    private fun setup() {
        val appFileName = fileManager.getFilePath(filename)
        Database.connect("jdbc:$PROTO:$appFileName$EXTRA_PARAMS", driver = DRIVER)

        transaction {
            SchemaUtils.create(
                ProjectEntity,
                LanguageEntity,
                SegmentEntity,
                MemoryEntryEntity,
                MemoryMessageEntity,
                GlossaryTermEntity,
                GlossaryTermRelationshipEntity,
                RecentProjectEntity,
            )
        }
    }

    fun projectDao() = DefaultProjectDao()
    fun languageDao() = DefaultLanguageDao()
    fun segmentDao() = DefaultSegmentDao()
    fun memoryEntryDao() = DefaultMemoryEntryDao()
    fun glossaryTermDao() = DefaultGlossaryTermDao()
    fun recentProjectDao() = DefaultRecentProjectDao()
}
