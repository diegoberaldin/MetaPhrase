package language.di

import language.repo.FlagsRepository
import language.repo.LanguageNameRepository
import language.repo.LanguageRepository
import language.usecase.GetCompleteLanguageUseCase
import org.koin.dsl.module

val languageModule = module {
    single {
        LanguageRepository(
            dao = get(),
        )
    }
    single {
        LanguageNameRepository()
    }
    single {
        FlagsRepository()
    }
    single {
        GetCompleteLanguageUseCase(
            languageNameRepository = get(),
            flagsRepository = get(),
        )
    }
}
