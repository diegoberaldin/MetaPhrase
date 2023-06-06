package persistence

import common.files.FileManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.dao.DefaultGlossaryTermDao
import persistence.dao.DefaultLanguageDao
import persistence.dao.DefaultMemoryEntryDao
import persistence.dao.DefaultProjectDao
import persistence.dao.DefaultSegmentDao
import persistence.entities.GlossaryTermEntity
import persistence.entities.GlossaryTermRelationshipEntity
import persistence.entities.LanguageEntity
import persistence.entities.MemoryEntryEntity
import persistence.entities.MemoryMessageEntity
import persistence.entities.ProjectEntity
import persistence.entities.SegmentEntity

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
