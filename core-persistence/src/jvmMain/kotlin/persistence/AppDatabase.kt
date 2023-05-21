package persistence

import common.files.FileManager
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.dao.LanguageDao
import persistence.dao.ProjectDao
import persistence.dao.SegmentDao
import persistence.entities.LanguageEntity
import persistence.entities.ProjectEntity
import persistence.entities.SegmentEntity

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
            )
        }
    }

    internal fun projectDao() = ProjectDao()
    internal fun languageDao() = LanguageDao()
    internal fun segmentDao() = SegmentDao()
}
