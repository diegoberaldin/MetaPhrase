package com.github.diegoberaldin.metaphrase.feature.translate.di

import com.github.diegoberaldin.metaphrase.domain.formats.di.formatsModule
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.di.dialogNewSegmentModule
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.di.dialogNewTermModule
import com.github.diegoberaldin.metaphrase.feature.translate.messages.di.translateMessagesModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.di.panelGlossaryModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.di.panelMachineTranslationModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.di.panelMatchesModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.di.panelMemoryModule
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.di.panelValidateModule
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.DefaultTranslateComponent
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.di.translateToolbarModule
import org.koin.dsl.module

val translateModule = module {
    includes(formatsModule)

    includes(translateToolbarModule)
    includes(translateMessagesModule)

    includes(
        dialogNewSegmentModule,
        dialogNewTermModule,
    )

    includes(
        panelMatchesModule,
        panelValidateModule,
        panelMemoryModule,
        panelGlossaryModule,
        panelMachineTranslationModule,
    )

    factory<TranslateComponent> { params ->
        DefaultTranslateComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            recentProjectRepository = get(),
            projectRepository = get(),
            languageRepository = get(),
            segmentRepository = get(),
            importResources = get(),
            importSegments = get(),
            exportResources = get(),
            validatePlaceholders = get(),
            notificationCenter = get(),
            exportToTmx = get(),
            validateSpelling = get(),
            syncProjectWithTm = get(),
            glossaryTermRepository = get(),
            saveProject = get(),
            machineTranslationRepository = get(),
            keyStore = get(),
        )
    }
}
