package com.github.diegoberaldin.metaphrase.feature.main.di

import com.github.diegoberaldin.metaphrase.feature.intro.di.introModule
import com.github.diegoberaldin.metaphrase.feature.main.dialog.settings.di.dialogSettingsModule
import com.github.diegoberaldin.metaphrase.feature.main.presentation.DefaultRootComponent
import com.github.diegoberaldin.metaphrase.feature.main.presentation.RootComponent
import com.github.diegoberaldin.metaphrase.feature.projects.di.projectsModule
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.di.dialogExportModule
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.di.dialogImportModule
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.newproject.di.dialogNewProjectModule
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.statistics.di.dialogStatisticsModule
import org.koin.dsl.module

/**
 * DI module for the main subproject.
 */
val mainModule = module {
    includes(projectsModule)
    includes(introModule)
    includes(dialogSettingsModule)
    includes(dialogNewProjectModule)
    includes(dialogStatisticsModule)
    includes(dialogExportModule)
    includes(dialogImportModule)

    factory<RootComponent> { params ->
        DefaultRootComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            recentProjectRepository = get(),
            projectRepository = get(),
            dispatchers = get(),
            notificationCenter = get(),
            importFromTmx = get(),
            clearTranslationMemory = get(),
            importGlossaryTerms = get(),
            exportGlossaryTerms = get(),
            clearGlossaryTerms = get(),
            openProjectUseCase = get(),
            machineTranslationRepository = get(),
        )
    }
}
