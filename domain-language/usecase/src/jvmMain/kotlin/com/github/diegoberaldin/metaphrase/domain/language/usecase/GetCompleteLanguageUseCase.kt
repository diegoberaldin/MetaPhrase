package com.github.diegoberaldin.metaphrase.domain.language.usecase

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

interface GetCompleteLanguageUseCase {
    operator fun invoke(lang: LanguageModel): LanguageModel
}
