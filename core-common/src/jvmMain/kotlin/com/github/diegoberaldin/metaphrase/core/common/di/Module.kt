package com.github.diegoberaldin.metaphrase.core.common.di

import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import com.github.diegoberaldin.metaphrase.core.common.keystore.DefaultTemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.log.DefaultLogManager
import com.github.diegoberaldin.metaphrase.core.common.log.LogManager
import com.github.diegoberaldin.metaphrase.core.common.notification.DefaultNotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import org.koin.dsl.module

/**
 * DI module for the core-common subproject.
 */
val commonModule = module {
    single<com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider> {
        com.github.diegoberaldin.metaphrase.core.common.coroutines.DefaultCoroutineDispatcherProvider
    }
    single<NotificationCenter> {
        DefaultNotificationCenter
    }
    single<TemporaryKeyStore> {
        DefaultTemporaryKeyStore(fileManager = get())
    }
    single<FileManager> {
        com.github.diegoberaldin.metaphrase.core.common.files.DefaultFileManager
    }
    single<LogManager> {
        DefaultLogManager(fileManager = get())
    }
}
