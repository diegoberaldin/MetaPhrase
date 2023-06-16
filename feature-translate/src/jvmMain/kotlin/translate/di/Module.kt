package translate.di

import dialognewsegment.di.dialogNewSegmentModule
import dialognewterm.di.dialogNewTermModule
import formats.di.formatsModule
import org.koin.dsl.module
import panelglossary.di.panelGlossaryModule
import panelmatches.di.panelMatchesModule
import panelmemory.di.panelMemoryModule
import panelvalidate.di.panelValidateModule
import translate.presentation.DefaultTranslateComponent
import translate.presentation.TranslateComponent
import translatemessages.di.translateMessagesModule
import translatetoolbar.di.translateToolbarModule

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
    )

    factory<TranslateComponent> { params ->
        DefaultTranslateComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
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
        )
    }
}
