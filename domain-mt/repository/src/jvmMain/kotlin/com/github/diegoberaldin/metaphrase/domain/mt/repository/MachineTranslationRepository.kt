package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import java.io.File

/**
 * Contract for the machine translation repository.
 */
interface MachineTranslationRepository {
    companion object {
        /**
         * List of all available machine translation providers.
         */
        val AVAILABLE_PROVIDERS = listOf(
            MachineTranslationProvider.MY_MEMORY,
        )
    }

    /**
     * Get a suggestion (translation) from machine translation.
     *
     * @param provider Machine translation provider
     * @param key API key for the provider (optional)
     * @param message Message to translate
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @return a suggestion from the MT provider
     */
    suspend fun getTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        key: String? = null,
        message: String,
        sourceLang: String,
        targetLang: String,
    ): String

    /**
     * Share a translation with the machine translation provider i.e. contribute a segment to the remote TM.
     * This implies transferring data to a third party engine, so handle with care.
     *
     * @param provider Machine translation provider
     * @param key API key (optional)
     * @param sourceMessage Source message
     * @param sourceLang Source language code
     * @param targetMessage Target message
     * @param targetLang Target language code
     */
    suspend fun shareTranslation(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        key: String? = null,
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
    )

    /**
     * Generate an API key for a machine translation provider.
     * This is supported only by some providers, e.g. [MachineTranslationProvider.MY_MEMORY]
     *
     * @param provider Provider
     * @param username Username of the account
     * @param password Password of the account
     * @return a valid API key for queries
     */
    suspend fun generateKey(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        username: String,
        password: String,
    ): String

    /**
     * Contribute a whole TMX file to the remote service.
     * This implies transferring data to a third party engine, so handle with care.
     *
     * @param provider Provider
     * @param file File of the TM to share
     * @param key API key (optional)
     * @param private if set to `true` the TM will not be shared with other users of the service (only if API key is specified)
     * @param name Name of the TM
     * @param subject Subject of the TM
     */
    suspend fun importTm(
        provider: MachineTranslationProvider = MachineTranslationProvider.MY_MEMORY,
        file: File,
        key: String? = null,
        private: Boolean = false,
        name: String? = null,
        subject: String? = null,
    )
}
