package com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.di

import com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation.BrowseMemoryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation.DefaultBrowseMemoryComponent
import org.koin.dsl.module

val panelMemoryModule = module {
    factory<BrowseMemoryComponent> { params ->
        DefaultBrowseMemoryComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            memoryEntryRepository = get(),
            completeLanguage = get(),
        )
    }
}
