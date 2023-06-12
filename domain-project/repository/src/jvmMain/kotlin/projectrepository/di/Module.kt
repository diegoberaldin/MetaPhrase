package projectrepository.di

import org.koin.dsl.module
import projectrepository.DefaultFlagsRepository
import projectrepository.DefaultLanguageNameRepository
import projectrepository.DefaultLanguageRepository
import projectrepository.DefaultProjectRepository
import projectrepository.DefaultSegmentRepository
import projectrepository.FlagsRepository
import projectrepository.LanguageNameRepository
import projectrepository.LanguageRepository
import projectrepository.ProjectRepository
import projectrepository.SegmentRepository

val projectRepositoryModule = module {
    single<ProjectRepository> {
        DefaultProjectRepository(
            dao = get(),
        )
    }
    single<SegmentRepository> {
        DefaultSegmentRepository(
            dao = get(),
        )
    }
    single<LanguageRepository> {
        DefaultLanguageRepository(
            dao = get(),
        )
    }
    single<LanguageNameRepository> {
        DefaultLanguageNameRepository()
    }
    single<FlagsRepository> {
        DefaultFlagsRepository()
    }
}
