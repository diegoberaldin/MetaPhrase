package com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.di

import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation.CreateProjectComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.presentation.DefaultCreateProjectComponent
import org.koin.dsl.module

val dialogNewProjectModule = module {
    factory<CreateProjectComponent> { params ->
        DefaultCreateProjectComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            projectRepository = get(),
            segmentRepository = get(),
            completeLanguage = get(),
        )
    }
}
