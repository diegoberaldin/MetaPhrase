package main.di

import dialognewproject.di.dialogNewProjectModule
import dialogsettings.di.dialogSettingsModule
import dialogstatistics.di.dialogStatisticsModule
import intro.di.introModule
import main.presentation.DefaultRootComponent
import main.presentation.RootComponent
import org.koin.dsl.module
import projects.di.projectsModule

val mainModule = module {
    includes(projectsModule)
    includes(introModule)
    includes(dialogSettingsModule)
    includes(dialogNewProjectModule)
    includes(dialogStatisticsModule)

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
