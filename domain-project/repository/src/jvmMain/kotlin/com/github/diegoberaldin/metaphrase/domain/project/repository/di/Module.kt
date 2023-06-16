package com.github.diegoberaldin.metaphrase.domain.project.repository.di

import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultFlagsRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultLanguageNameRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultLanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultSegmentRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.FlagsRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.LanguageNameRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import org.koin.dsl.module

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
