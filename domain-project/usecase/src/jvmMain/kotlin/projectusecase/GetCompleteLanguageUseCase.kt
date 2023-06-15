package projectusecase

import projectdata.LanguageModel

interface GetCompleteLanguageUseCase {
    operator fun invoke(lang: LanguageModel): LanguageModel
}
