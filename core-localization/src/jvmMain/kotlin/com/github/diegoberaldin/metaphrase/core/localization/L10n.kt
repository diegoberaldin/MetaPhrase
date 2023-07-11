package com.github.diegoberaldin.metaphrase.core.localization

import com.github.diegoberaldin.metaphrase.core.localization.repository.Localization
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import org.koin.java.KoinJavaComponent.inject

/**
 * Global entry point for the app localization.
 */
object L10n {

    private val default: Localization by inject(Localization::class.java)

    /**
     * Exposes the ISO 693-1 code of the current language as an observable flow.
     */
    val currentLanguage = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val value = get("lang")
            trySend(value)
            delay(1_000)
        }
    }.distinctUntilChanged()

    /**
     * Set the app language.
     *
     * @param lang Language code (ISO 639-1 code as a 2 character string)
     */
    fun setLanguage(lang: String) {
        default.setLanguage(lang)
    }

    /**
     * Get the message associated to a given key. If the key is not present in the current language, a default value
     * (base language) should be returned. If the key does not exist even in the base bundle, the key itself should be returned.
     *
     * @param key Message key
     * @return the localized string to show in the app UI
     */
    fun get(key: String): String = default.get(key)

    /**
     * Get the message associated to a given key with format arguments.
     *
     * @param key Message key
     * @param args Format arguments
     * @return the localized string to show in the app UI
     */
    fun get(key: String, vararg args: Any): String {
        return default.get(key).format(*args)
    }
}

/**
 * Convenience extension that can be invoked on a key to get a translation.
 *
 * @receiver Message key
 * @return Message localized version to show in the UI
 */
fun String.localized(): String {
    return L10n.get(this)
}

/**
 * Convenience extension that can be invoked on a key to get a translation with format arguments.
 *
 * @receiver Message key
 * @param args Format arguments
 * @return Message localized version to show in the UI
 */
fun String.localized(vararg args: Any): String {
    return L10n.get(key = this, args = args)
}
