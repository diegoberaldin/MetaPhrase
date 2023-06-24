package com.github.diegoberaldin.metaphrase.domain.language.repository

/**
 * Contract for the language name repository.
 */
interface LanguageNameRepository {
    /**
     * Get a user-friendly name for the language based on its ISO code (ISO 693-1 standard).
     * This returns a value only for the supported languages (see [LanguageRepository.getDefaultLanguages].
     *
     * @param code ISO 693-1 language code
     * @return a user-friendly name of the language
     */
    fun getName(code: String): String
}
