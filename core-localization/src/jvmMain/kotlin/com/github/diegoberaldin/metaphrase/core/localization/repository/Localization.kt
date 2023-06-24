package com.github.diegoberaldin.metaphrase.core.localization.repository

/**
 * Defines the contract for the localization repository.
 */
interface Localization {
    /**
     * Get a message translation for a given key.
     *
     * @param key Message key
     * @return Message to show in app
     */
    fun get(key: String): String

    /**
     * Set the app language globally.
     *
     * @param lang Language code (ISO 639-1)
     */
    fun setLanguage(lang: String)

    /**
     * Get the current language.
     *
     * @return Language code in the ISO 693-1 format
     */
    fun getLanguage(): String
}
