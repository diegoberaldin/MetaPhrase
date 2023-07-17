package com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.di

import com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation.DefaultImportDialogComponent
import com.github.diegoberaldin.metaphrase.feature.projects.dialog.import.presentation.ImportDialogComponent
import org.koin.dsl.module

val dialogImportModule = module {
    single<ImportDialogComponent> {
        DefaultImportDialogComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            languageRepository = get(),
            completeLanguage = get(),
            importResources = get(),
        )
    }
}
