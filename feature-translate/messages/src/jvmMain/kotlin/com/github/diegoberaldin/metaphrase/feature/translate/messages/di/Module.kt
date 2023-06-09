package com.github.diegoberaldin.metaphrase.feature.translate.messages.di

import com.github.diegoberaldin.metaphrase.domain.spellcheck.di.spellcheckModule
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.DefaultMessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import org.koin.dsl.module

/**
 * DI module for the message list subproject
 */
val translateMessagesModule = module {
    includes(spellcheckModule)

    factory<MessageListComponent> { params ->
        DefaultMessageListComponent(
            componentContext = params[0],
            coroutineContext = params[1],
            dispatchers = get(),
            projectRepository = get(),
            languageRepository = get(),
            segmentRepository = get(),
            spellCheckRepository = get(),
            notificationCenter = get(),
            keyStore = get(),
        )
    }
}
