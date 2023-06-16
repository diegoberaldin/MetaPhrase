package com.github.diegoberaldin.metaphrase.core.persistence

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.DefaultGlossaryTermDao
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities.GlossaryTermEntity
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.entities.GlossaryTermRelationshipEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultLanguageDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.DefaultSegmentDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.LanguageEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.ProjectEntity
import com.github.diegoberaldin.metaphrase.domain.project.persistence.entities.SegmentEntity
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.DefaultMemoryEntryDao
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryEntryEntity
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.entities.MemoryMessageEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

internal class AppDatabase(
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
            )
        }
    }

    internal fun projectDao() = DefaultProjectDao()
    internal fun languageDao() = DefaultLanguageDao()
    internal fun segmentDao() = DefaultSegmentDao()
    internal fun memoryEntryDao() = DefaultMemoryEntryDao()
    internal fun glossaryTermDao() = DefaultGlossaryTermDao()
}
