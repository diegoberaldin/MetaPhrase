package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

/**
 * Contract for the get glossary terms use case.
 */
interface GetGlossaryTermsUseCase {
    /**
     * Retrieve the glossary terms (source) for a source message. This should consider the stem of the words
     * contained in the message.
     *
     * @param message Message text
     * @param lang Language code
     * @return list of all the matches from the glossary for the text
     */
    suspend operator fun invoke(message: String, lang: String): List<GlossaryTermModel>
}
