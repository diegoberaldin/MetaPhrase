package com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase

/**
 * Contract of the validate spelling use case.
 */
interface ValidateSpellingUseCase {

    /**
     * Input parameter object.
     *
     * @property key message key
     * @property message message text
     * @constructor Create [InputItem]
     */
    data class InputItem(
        val key: String,
        val message: String,
    )

    /**
     * Check for spelling errors a given messaeg.
     *
     * @param input operation input
     * @param lang language code
     * @return errors in the form of a map from message key to list of misspelled words
     */
    suspend operator fun invoke(input: List<InputItem>, lang: String): Map<String, List<String>>
}
