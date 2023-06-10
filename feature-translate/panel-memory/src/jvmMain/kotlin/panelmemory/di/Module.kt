package panelmemory.di

import org.koin.dsl.module
import panelmemory.presentation.BrowseMemoryComponent
import panelmemory.presentation.DefaultBrowseMemoryComponent

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
