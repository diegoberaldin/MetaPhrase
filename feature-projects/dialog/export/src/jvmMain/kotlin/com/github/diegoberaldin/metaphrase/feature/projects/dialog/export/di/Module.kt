package com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.di

import com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.presentation.DefaultExportDialogComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.export.presentation.ExportDialogComponent
import org.koin.dsl.module

/**
 * DI module for the export dialog subproject.
 */
val dialogExportModule = module {
    factory<ExportDialogComponent> { params ->
        DefaultExportDialogComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
            completeLanguage = get(),
            exportResources = get(),
        )
    }
}
