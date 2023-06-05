package panelglossary.di

import org.koin.dsl.module
import panelglossary.ui.DefaultGlossaryComponent
import panelglossary.ui.GlossaryComponent

val panelGlossaryModule = module {
    factory<GlossaryComponent> {
        DefaultGlossaryComponent(
            componentContext = it[0],
            coroutineContext = it[1],
        )
    }
}
