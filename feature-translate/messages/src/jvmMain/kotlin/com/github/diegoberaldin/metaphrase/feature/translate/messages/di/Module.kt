package com.github.diegoberaldin.metaphrase.feature.translate.messages.di

import com.github.diegoberaldin.metaphrase.domain.spellcheck.di.spellcheckModule
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.DefaultMessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import org.koin.dsl.module

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
