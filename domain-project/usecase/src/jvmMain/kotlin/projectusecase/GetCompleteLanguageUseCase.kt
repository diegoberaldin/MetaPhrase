package projectusecase

import data.LanguageModel

interface GetCompleteLanguageUseCase {
    operator fun invoke(lang: LanguageModel): LanguageModel
}
