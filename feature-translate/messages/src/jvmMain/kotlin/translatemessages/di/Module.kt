package translatemessages.di

import org.koin.dsl.module
import spellcheck.spellcheckModule
import translatemessages.ui.DefaultMessageListComponent
import translatemessages.ui.MessageListComponent

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
        )
    }
}
