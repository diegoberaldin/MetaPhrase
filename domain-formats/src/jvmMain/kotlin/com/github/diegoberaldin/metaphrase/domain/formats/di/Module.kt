package com.github.diegoberaldin.metaphrase.domain.formats.di

import com.github.diegoberaldin.metaphrase.domain.formats.android.DefaultParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.ExportAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.ParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.DefaultExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.DefaultParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.DefaultExportPoResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.DefaultParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ExportPoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.DefaultExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.DefaultParseResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ParseResxUseCase
import org.koin.dsl.module

val androidModule = module {
    single<ParseAndroidResourcesUseCase> {
        DefaultParseAndroidResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportAndroidResourcesUseCase> {
        com.github.diegoberaldin.metaphrase.domain.formats.android.DefaultExportAndroidResourcesUseCase(
            dispatchers = get(),
        )
    }
}

val iosModule = module {
    single<ParseIosResourcesUseCase> {
        DefaultParseIosResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportIosResourcesUseCase> {
        DefaultExportIosResourcesUseCase(
            dispatchers = get(),
        )
    }
}

private val resxModule = module {
    single<ParseResxUseCase> {
        DefaultParseResxUseCase(
            dispatchers = get(),
        )
    }
    single<ExportResxUseCase> {
        DefaultExportResxUseCase(
            dispatchers = get(),
        )
    }
}

val poModule = module {
    single<ParsePoUseCase> {
        DefaultParsePoUseCase(
            dispatchers = get(),
        )
    }
    single<ExportPoUseCase> {
        DefaultExportPoResourcesUseCase(
            dispatchers = get(),
        )
    }
}

val formatsModule = module {
    includes(
        androidModule,
        iosModule,
        resxModule,
        poModule,
    )
    single<com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesUseCase> {
        com.github.diegoberaldin.metaphrase.domain.formats.DefaultImportResourcesUseCase(
            parseAndroid = get(),
            parseIos = get(),
            parseResx = get(),
            parsePo = get(),
        )
    }
    single<com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesUseCase> {
        com.github.diegoberaldin.metaphrase.domain.formats.DefaultExportResourcesUseCase(
            exportAndroid = get(),
            exportIos = get(),
            exportResx = get(),
            exportPo = get(),
        )
    }
}
