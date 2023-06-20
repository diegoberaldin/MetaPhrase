package com.github.diegoberaldin.metaphrase.domain.project.repository.di

import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultRecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.DefaultSegmentRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
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
    single<RecentProjectRepository> {
        DefaultRecentProjectRepository(
            dao = get(),
        )
    }
}
