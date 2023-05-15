package common.di

import common.files.FileManager
import common.keystore.DefaultTemporaryKeyStore
import common.keystore.TemporaryKeyStore
import common.log.DefaultLogManager
import common.log.LogManager
import common.notification.DefaultNotificationCenter
import common.notification.NotificationCenter
import org.koin.dsl.module

val commonModule = module {
    single<common.coroutines.CoroutineDispatcherProvider> {
        common.coroutines.CoroutineDispatcherProviderImpl
    }
    single<NotificationCenter> {
        DefaultNotificationCenter
    }
    single<TemporaryKeyStore> {
        DefaultTemporaryKeyStore(fileManager = get())
    }
    single<FileManager> {
        common.files.DefaultFileManager
    }
    single<LogManager> {
        DefaultLogManager(fileManager = get())
    }
}
