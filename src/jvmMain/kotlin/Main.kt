import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import common.di.commonModule
import common.keystore.TemporaryKeyStore
import common.log.LogManager
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent
import java.util.*

private fun initKoin() {
    startKoin {
        modules(
            commonModule,
        )
    }
}

fun main() = application {
    // init DI
    initKoin()

    runBlocking {
        // init l10n
        val keystore: TemporaryKeyStore by KoinJavaComponent.inject(TemporaryKeyStore::class.java)
        val systemLanguage = Locale.getDefault().language
        val lang = keystore.get("lang", "")
        L10n.setLanguage(lang.ifEmpty { systemLanguage })
        if (lang.isEmpty()) {
            keystore.save("lang", "lang".localized())
        }
    }

    val log: LogManager by KoinJavaComponent.inject(LogManager::class.java)
    log.debug("Application initialized")

    Window(onCloseRequest = ::exitApplication, title = "app_name".localized()) {
        val lang by L10n.currentLanguage.collectAsState("lang".localized())
        LaunchedEffect(lang) {}

        App()
    }
}

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}
