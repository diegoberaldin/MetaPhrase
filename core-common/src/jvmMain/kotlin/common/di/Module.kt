package common.di

import common.coroutines.CoroutineDispatcherProvider
import common.coroutines.DefaultCoroutineDispatcherProvider
import common.files.DefaultFileManager
import common.files.FileManager
import common.keystore.DefaultTemporaryKeyStore
import common.keystore.TemporaryKeyStore
import common.log.DefaultLogManager
import common.log.LogManager
import common.notification.DefaultNotificationCenter
import common.notification.NotificationCenter
import org.koin.dsl.module

val commonModule = module {
    single<CoroutineDispatcherProvider> {
        DefaultCoroutineDispatcherProvider
    }
    single<NotificationCenter> {
        DefaultNotificationCenter
    }
    single<TemporaryKeyStore> {
        DefaultTemporaryKeyStore(fileManager = get())
    }
    single<FileManager> {
        DefaultFileManager
    }
    single<LogManager> {
        DefaultLogManager(fileManager = get())
    }
}
