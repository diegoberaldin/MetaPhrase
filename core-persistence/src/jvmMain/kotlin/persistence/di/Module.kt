package persistence.di

import glossarypersistence.dao.GlossaryTermDao
import org.koin.dsl.module
import persistence.AppDatabase
import persistence.dao.LanguageDao
import persistence.dao.MemoryEntryDao
import persistence.dao.ProjectDao
import persistence.dao.SegmentDao

private val dbModule = module {
    single {
        AppDatabase(
            fileManager = get(),
        )
    }
}

private val daoModule = module {
    single<ProjectDao> {
        val db: AppDatabase = get()
        db.projectDao()
    }
    single<LanguageDao> {
        val db: AppDatabase = get()
        db.languageDao()
    }
    single<SegmentDao> {
        val db: AppDatabase = get()
        db.segmentDao()
    }
    single<MemoryEntryDao> {
        val db: AppDatabase = get()
        db.memoryEntryDao()
    }
    single<GlossaryTermDao> {
        val db: AppDatabase = get()
        db.glossaryTermDao()
    }
}

val persistenceModule = module {
    includes(dbModule)
    includes(daoModule)
}
