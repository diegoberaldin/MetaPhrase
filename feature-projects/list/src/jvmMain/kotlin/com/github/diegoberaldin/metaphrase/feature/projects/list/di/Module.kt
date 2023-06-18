package com.github.diegoberaldin.metaphrase.feature.projects.list.di

import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.DefaultProjectListComponent
import com.github.diegoberaldin.metaphrase.feature.projects.list.presentation.ProjectListComponent
import org.koin.dsl.module

val projectListModule = module {
    factory<ProjectListComponent> { params ->
        DefaultProjectListComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            projectRepository = get(),
            notificationCenter = get(),
            openProject = get(),
        )
    }
}
