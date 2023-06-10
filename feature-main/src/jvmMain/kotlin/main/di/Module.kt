package main.di

import intro.di.introModule
import main.presentation.DefaultRootComponent
import main.presentation.RootComponent
import mainsettings.di.mainSettingsModule
import org.koin.dsl.module
import projects.di.projectsModule
import projectscreate.di.createProjectModule
import projectstatistics.di.projectStatisticsModule

val mainModule = module {
    includes(projectsModule)
    includes(introModule)
    includes(mainSettingsModule)
    includes(createProjectModule)
    includes(projectStatisticsModule)

    factory<RootComponent> { params ->
        DefaultRootComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            projectRepository = get(),
            dispatchers = get(),
            notificationCenter = get(),
            importFromTmx = get(),
            clearTranslationMemory = get(),
        )
    }
}
