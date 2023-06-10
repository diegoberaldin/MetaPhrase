package translatemessages.di

import org.koin.dsl.module
import spellcheck.di.spellcheckModule
import translatemessages.presentation.DefaultMessageListComponent
import translatemessages.presentation.MessageListComponent

val translateMessagesModule = module {
    includes(spellcheckModule)

    factory<MessageListComponent> { params ->
        DefaultMessageListComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            segmentRepository = get(),
            languageRepository = get(),
            spellCheckRepository = get(),
            notificationCenter = get(),
            keyStore = get(),
        )
    }
}
