package com.github.diegoberaldin.metaphrase.core.localization.di

import com.github.diegoberaldin.metaphrase.core.localization.repository.DefaultLocalization
import com.github.diegoberaldin.metaphrase.core.localization.repository.Localization
import com.github.diegoberaldin.metaphrase.core.localization.usecase.DefaultParseXmlResourceUseCase
import com.github.diegoberaldin.metaphrase.core.localization.usecase.ParseXmlResourceUseCase
import org.koin.dsl.module

val localizationModule = module {
    single<Localization> {
        DefaultLocalization(
            parseResource = get(),
        )
    }
    single<ParseXmlResourceUseCase> {
        DefaultParseXmlResourceUseCase()
    }
}
