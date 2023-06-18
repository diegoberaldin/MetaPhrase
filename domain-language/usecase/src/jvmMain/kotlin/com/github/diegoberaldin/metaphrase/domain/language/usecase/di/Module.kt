package com.github.diegoberaldin.metaphrase.domain.language.usecase.di

import com.github.diegoberaldin.metaphrase.domain.language.usecase.DefaultGetCompleteLanguageUseCase
import com.github.diegoberaldin.metaphrase.domain.language.usecase.GetCompleteLanguageUseCase
import org.koin.dsl.module

val languageUseCaseModule = module {
    single<GetCompleteLanguageUseCase> {
        DefaultGetCompleteLanguageUseCase(
            languageNameRepository = get(),
            flagsRepository = get(),
        )
    }
}
