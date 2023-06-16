package com.github.diegoberaldin.metaphrase.domain.tm.repository.di

import com.github.diegoberaldin.metaphrase.domain.tm.repository.DefaultMemoryEntryRepository
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import org.koin.dsl.module

val tmRepositoryModule = module {
    single<MemoryEntryRepository> {
        DefaultMemoryEntryRepository(
            dao = get(),
        )
    }
}
