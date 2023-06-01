package translate.di

import org.koin.dsl.module
import translate.ui.DefaultTranslateComponent
import translate.ui.TranslateComponent
import translateinvalidsegments.di.translateInvalidSegmentsModule
import translatemessages.di.translateMessagesModule
import translatenewsegment.di.translateNewSegmentModule
import translatetoolbar.di.translateToolbarModule
import translationtranslationmemory.di.translateTranslationMemoryModule

val translateModule = module {
    includes(translateToolbarModule)
    includes(translateMessagesModule)
    includes(translateInvalidSegmentsModule)
    includes(translateNewSegmentModule)
    includes(translateTranslationMemoryModule)

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
            importFromTmx = get(),
        )
    }
}
