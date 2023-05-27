package main.di

import intro.di.introModule
import main.ui.DefaultRootComponent
import main.ui.RootComponent
import org.koin.dsl.module
import projects.di.projectsModule
import projectscreate.di.createProjectModule
import projectstatistics.di.projectStatisticsModule

val mainModule = module {
    includes(projectsModule)
    includes(introModule)
    includes(createProjectModule)
    includes(projectStatisticsModule)

    factory<RootComponent> { params ->
        DefaultRootComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            projectRepository = get(),
            dispatchers = get(),
            notificationCenter = get(),
        )
    }
}
