package com.github.diegoberaldin.metaphrase.domain.language.usecase

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

/**
 * Contract for the get complete language use case.
 */
interface GetCompleteLanguageUseCase {
    /**
     * Complete the language populating its name with a user-friendly label and a flag.
     *
     * @param lang Language to complete
     * @return [LanguageModel] the completed language (ID is maintained)
     */
    operator fun invoke(lang: LanguageModel): LanguageModel
}
