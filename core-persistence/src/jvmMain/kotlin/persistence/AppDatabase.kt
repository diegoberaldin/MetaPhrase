package persistence

import common.files.FileManager
import glossarypersistence.dao.DefaultGlossaryTermDao
import glossarypersistence.entities.GlossaryTermEntity
import glossarypersistence.entities.GlossaryTermRelationshipEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import projectpersistence.dao.DefaultLanguageDao
import projectpersistence.dao.DefaultProjectDao
import projectpersistence.dao.DefaultSegmentDao
import projectpersistence.entities.LanguageEntity
import projectpersistence.entities.ProjectEntity
import projectpersistence.entities.SegmentEntity
import tmpersistence.dao.DefaultMemoryEntryDao
import tmpersistence.entities.MemoryEntryEntity
import tmpersistence.entities.MemoryMessageEntity

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
