package com.github.diegoberaldin.metaphrase.domain.language.repository

/**
 * Contract for the flag repository.
 */
interface FlagsRepository {
    /**
     * Get a Unicode representation of the flag of the language having a given code.
     * This returns a value only for the supported languages (see [LanguageRepository.getDefaultLanguages].
     *
     * @param code ISO 693-1 language code
     * @return an emoji corresponding to the country flag
     */
    fun getFlag(code: String): String
}
