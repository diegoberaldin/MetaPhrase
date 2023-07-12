package com.github.diegoberaldin.metaphrase.domain.mt.repository

import com.github.diegoberaldin.metaphrase.domain.mt.repository.data.MachineTranslationProvider
import kotlinx.coroutines.flow.StateFlow
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
            MachineTranslationProvider.DEEPL,
        )
    }

    /**
     * True if the current providers supports contributing individual messages or TMs.
     */
    val supportsContributions: StateFlow<Boolean>

    /**
     * True if the current provider supports generating API keys.
     */
    val supportsKeyGeneration: StateFlow<Boolean>

    /**
     * Set the current machine translation provider.
     *
     * @param provider Machine translation provider
     */
    fun setProvider(provider: MachineTranslationProvider)

    /**
     * Get a suggestion (translation) from machine translation.
     *
     * @param key API key for the provider (optional)
     * @param sourceMessage Message to translate
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @return a suggestion from the MT provider
     */
    suspend fun getTranslation(
        key: String? = null,
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
    ): String

    /**
     * Share a translation with the machine translation provider i.e. contribute a segment to the remote TM.
     * This implies transferring data to a third party engine, so handle with care.
     *
     * @param key API key (optional)
     * @param sourceMessage Source message
     * @param sourceLang Source language code
     * @param targetMessage Target message
     * @param targetLang Target language code
     */
    suspend fun shareTranslation(
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
     * @param username Username of the account
     * @param password Password of the account
     * @return a valid API key for queries
     */
    suspend fun generateKey(
        username: String,
        password: String,
    ): String

    /**
     * Contribute a whole TMX file to the remote service.
     * This implies transferring data to a third party engine, so handle with care.
     *
     * @param file File of the TM to share
     * @param key API key (optional)
     * @param private if set to `true` the TM will not be shared with other users of the service (only if API key is specified)
     * @param name Name of the TM
     * @param subject Subject of the TM
     */
    suspend fun importTm(
        file: File,
        key: String? = null,
        private: Boolean = false,
        name: String? = null,
        subject: String? = null,
    )
}
