package projectscreate.di

import org.koin.dsl.module
import projectscreate.ui.CreateProjectComponent
import projectscreate.ui.DefaultCreateProjectComponent

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
