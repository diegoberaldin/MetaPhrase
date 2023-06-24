package com.github.diegoberaldin.metaphrase.domain.tm.repository.di

import com.github.diegoberaldin.metaphrase.domain.tm.repository.DefaultMemoryEntryRepository
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import org.koin.dsl.module

/**
 * DI module for the translation memory domain repository subproject.
 */
val tmRepositoryModule = module {
    single<MemoryEntryRepository> {
        DefaultMemoryEntryRepository(
            dao = get(),
        )
    }
}
