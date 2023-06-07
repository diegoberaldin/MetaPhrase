package language.usecase

import data.LanguageModel

interface GetCompleteLanguageUseCase {
    operator fun invoke(lang: LanguageModel): LanguageModel
}
