package com.github.diegoberaldin.metaphrase.domain.mt.repository.datasource

import java.io.File

/**
 * Contract for any machine translation connector with a remote provider.
 * This interface is just for documentation, since concrete implementations are injected in
 * [com.github.diegoberaldin.metaphrase.domain.mt.repository.DefaultMachineTranslationRepository].
 */
interface MachineTranslationDataSource {
    /**
     * Get a suggestion (translation) from the remote provider.
     *
     * @param sourceMessage Source message
     * @param sourceLang Source language code
     * @param targetLang Target language code
     * @param key API key (optional)
     * @return a translation for the message
     */
    suspend fun getTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetLang: String,
        key: String? = null,
    ): String

    /**
     * Share a translation to the remote provider.
     *
     * @param sourceMessage Source message
     * @param sourceLang Source language code
     * @param targetMessage Target message
     * @param targetLang Target language code
     * @param key API key (optional)
     */
    suspend fun contributeTranslation(
        sourceMessage: String,
        sourceLang: String,
        targetMessage: String,
        targetLang: String,
        key: String? = null,
    )

    /**
     * Generate an API key. This may not be supported by all providers. It is up to the implementation
     * whether to return an empty string or throw an exception if the operation is not supported.
     *
     * @param username Username of the account
     * @param password Password of the account
     * @return an API key
     */
    suspend fun generateKey(
        username: String,
        password: String,
    ): String

    /**
     * Import a TMX file to the remote provider.
     *
     * @param file File of the TM
     * @param key API key (optional)
     * @param private if set to `true` the segments will not be visible to other users of the remote provider (implies the `key` parameter is passed)
     * @param name Name of the TM (optional)
     * @param subject Subject of the TM (optional)
     */
    suspend fun import(
        file: File,
        key: String? = null,
        private: Boolean = false,
        name: String? = null,
        subject: String? = null,
    )
}
