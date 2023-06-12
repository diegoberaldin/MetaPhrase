package persistence.di

import glossarypersistence.dao.GlossaryTermDao
import org.koin.dsl.module
import persistence.AppDatabase
import projectpersistence.dao.LanguageDao
import projectpersistence.dao.ProjectDao
import projectpersistence.dao.SegmentDao
import tmpersistence.dao.MemoryEntryDao

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
