package language.di

import language.repo.DefaultFlagsRepository
import language.repo.DefaultLanguageNameRepository
import language.repo.DefaultLanguageRepository
import language.repo.FlagsRepository
import language.repo.LanguageNameRepository
import language.repo.LanguageRepository
import language.usecase.DefaultGetCompleteLanguageUseCase
import language.usecase.GetCompleteLanguageUseCase
import org.koin.dsl.module

val languageModule = module {
    single<LanguageRepository> {
        DefaultLanguageRepository(
            dao = get(),
        )
    }
    single<LanguageNameRepository> {
        DefaultLanguageNameRepository()
    }
    single<FlagsRepository> {
        DefaultFlagsRepository()
    }
    single<GetCompleteLanguageUseCase> {
        DefaultGetCompleteLanguageUseCase(
            languageNameRepository = get(),
            flagsRepository = get(),
        )
    }
}
