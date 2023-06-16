package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel

interface GetCompleteLanguageUseCase {
    operator fun invoke(lang: LanguageModel): LanguageModel
}
