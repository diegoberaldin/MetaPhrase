package com.github.diegoberaldin.metaphrase.core.localization

import com.github.diegoberaldin.metaphrase.core.localization.repository.Localization
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import org.koin.java.KoinJavaComponent.get

object L10n {

    private val default: Localization = get(Localization::class.java)

    val currentLanguage = channelFlow<String> {
        while (true) {
            if (!isActive) {
                break
            }
            trySend(get("lang"))
            delay(1_000)
        }
    }

    fun setLanguage(lang: String) {
        default.setLanguage(lang)
    }

    fun get(key: String): String = default.get(key)

    fun get(key: String, vararg args: Any): String {
        return default.get(key).format(*args)
    }
}

fun String.localized(): String {
    return L10n.get(this)
}

fun String.localized(vararg args: Any): String {
    return L10n.get(key = this, args = args)
}
