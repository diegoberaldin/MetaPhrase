package translate.di

import dialognewsegment.di.dialogNewSegmentModule
import dialognewterm.di.dialogNewTermModule
import formatsandroid.di.androidModule
import formatsios.di.iosModule
import formatspo.di.poModule
import formatsresx.di.resxModule
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
    includes(
        androidModule,
        iosModule,
        resxModule,
        poModule,
    )

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
            parseAndroidResources = get(),
            parseIosResources = get(),
            parseResx = get(),
            parsePo = get(),
            importSegments = get(),
            exportAndroidResources = get(),
            exportIosResources = get(),
            exportResx = get(),
            exportPo = get(),
            validatePlaceholders = get(),
            notificationCenter = get(),
            exportToTmx = get(),
            validateSpelling = get(),
            syncProjectWithTm = get(),
            glossaryTermRepository = get(),
        )
    }
}
