package translatebrowsememory.di

import org.koin.dsl.module
import translatebrowsememory.ui.BrowseMemoryComponent
import translatebrowsememory.ui.DefaultBrowseMemoryComponent

val translateBrowseMemoryModule = module {
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
