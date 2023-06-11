package dialognewproject.di

import dialognewproject.presentation.CreateProjectComponent
import dialognewproject.presentation.DefaultCreateProjectComponent
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
