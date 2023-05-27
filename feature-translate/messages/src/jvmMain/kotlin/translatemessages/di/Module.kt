package translatemessages.di

import org.koin.dsl.module
import translatemessages.ui.DefaultMessageListComponent
import translatemessages.ui.MessageListComponent

val translateMessagesModule = module {
    factory<MessageListComponent> { params ->
        DefaultMessageListComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            segmentRepository = get(),
            languageRepository = get(),
            notificationCenter = get(),
        )
    }
}
