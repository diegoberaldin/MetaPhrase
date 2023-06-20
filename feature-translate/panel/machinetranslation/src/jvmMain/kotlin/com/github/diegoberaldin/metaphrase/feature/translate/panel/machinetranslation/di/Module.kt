package com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.di

import com.github.diegoberaldin.metaphrase.domain.mt.di.machineTranslationModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation.DefaultMachineTranslationComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation.MachineTranslationComponent
import org.koin.dsl.module

val panelMachineTranslationModule = module {
    includes(machineTranslationModule)

    factory<MachineTranslationComponent> {
        DefaultMachineTranslationComponent(
            componentContext = it[0],
            coroutineContext = it[1],
            dispatchers = get(),
            languageRepository = get(),
            segmentRepository = get(),
            machineTranslationRepository = get(),
        )
    }
}
