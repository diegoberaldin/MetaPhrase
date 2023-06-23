package com.github.diegoberaldin.metaphrase.feature.projects.di

import com.github.diegoberaldin.metaphrase.feature.projects.list.di.projectListModule
import com.github.diegoberaldin.metaphrase.feature.projects.presentation.DefaultProjectsComponent
import com.github.diegoberaldin.metaphrase.feature.projects.presentation.ProjectsComponent
import com.github.diegoberaldin.metaphrase.feature.translate.di.translateModule
import org.koin.dsl.module

/**
 * DI module for the projects subproject.
 */
val projectsModule = module {
    includes(projectListModule)
    includes(translateModule)

    factory<ProjectsComponent> { params ->
        DefaultProjectsComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            keyStore = get(),
            projectRepository = get(),
            recentProjectRepository = get(),
            openProjectUseCase = get(),
        )
    }
}
