package newproject.di

import org.koin.dsl.module
import newproject.presentation.CreateProjectComponent
import newproject.presentation.DefaultCreateProjectComponent

val createProjectModule = module {
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
