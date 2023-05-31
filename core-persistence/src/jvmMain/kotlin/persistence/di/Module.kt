package persistence.di

import org.koin.dsl.module
import persistence.AppDatabase

private val dbModule = module {
    single {
        AppDatabase(
            fileManager = get(),
        )
    }
}

private val daoModule = module {
    single {
        val db: AppDatabase = get()
        db.projectDao()
    }
    single {
        val db: AppDatabase = get()
        db.languageDao()
    }
    single {
        val db: AppDatabase = get()
        db.segmentDao()
    }
    single {
        val db: AppDatabase = get()
        db.memoryEntryDao()
    }
}

val persistenceModule = module {
    includes(dbModule)
    includes(daoModule)
}
