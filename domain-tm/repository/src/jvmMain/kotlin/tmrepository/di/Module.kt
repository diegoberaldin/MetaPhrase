package tmrepository.di

import org.koin.dsl.module
import tmrepository.DefaultMemoryEntryRepository
import tmrepository.MemoryEntryRepository

val tmRepositoryModule = module {
    single<MemoryEntryRepository> {
        DefaultMemoryEntryRepository(
            dao = get(),
        )
    }
}
