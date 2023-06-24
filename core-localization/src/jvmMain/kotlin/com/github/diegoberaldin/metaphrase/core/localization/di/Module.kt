package com.github.diegoberaldin.metaphrase.core.localization.di

import com.github.diegoberaldin.metaphrase.core.localization.repository.DefaultLocalization
import com.github.diegoberaldin.metaphrase.core.localization.repository.Localization
import com.github.diegoberaldin.metaphrase.core.localization.usecase.ParseResourceUseCase
import com.github.diegoberaldin.metaphrase.core.localization.usecase.TmxParseResourceUseCase
import org.koin.dsl.module

/**
 * DI module for the core-localization subproject.
 */
val localizationModule = module {
    single<Localization> {
        DefaultLocalization(
            parseResource = get(),
        )
    }
    single<ParseResourceUseCase> {
        // XmlParseResourceUseCase()
        TmxParseResourceUseCase()
    }
}
