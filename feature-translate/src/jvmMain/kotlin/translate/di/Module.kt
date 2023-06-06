package translate.di

import android.di.androidModule
import org.koin.dsl.module
import panelglossary.di.panelGlossaryModule
import panelmatches.di.panelMatchesModule
import panelmemory.di.panelMemoryModule
import panelvalidate.di.panelValidateModule
import translate.ui.DefaultTranslateComponent
import translate.ui.TranslateComponent
import translatemessages.di.translateMessagesModule
import translatenewsegment.di.translateNewSegmentModule
import translatetoolbar.di.translateToolbarModule

val translateModule = module {
    includes(androidModule)
    includes(translateToolbarModule)
    includes(translateMessagesModule)
    includes(translateNewSegmentModule)
    includes(panelMatchesModule)
    includes(panelValidateModule)
    includes(panelMemoryModule)
    includes(panelGlossaryModule)

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
            importSegments = get(),
            exportAndroidResources = get(),
            exportIosResources = get(),
            validatePlaceholders = get(),
            notificationCenter = get(),
            exportToTmx = get(),
        )
    }
}
