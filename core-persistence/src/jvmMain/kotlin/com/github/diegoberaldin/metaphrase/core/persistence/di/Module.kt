package com.github.diegoberaldin.metaphrase.core.persistence.di

import com.github.diegoberaldin.metaphrase.core.persistence.AppDatabase
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.GlossaryTermDao
import com.github.diegoberaldin.metaphrase.domain.language.persistence.dao.LanguageDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.ProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.RecentProjectDao
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.SegmentDao
import com.github.diegoberaldin.metaphrase.domain.tm.persistence.dao.MemoryEntryDao
import org.koin.dsl.module

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
    single<RecentProjectDao> {
        val db: AppDatabase = get()
        db.recentProjectDao()
    }
}

val persistenceModule = module {
    includes(dbModule)
    includes(daoModule)
}
