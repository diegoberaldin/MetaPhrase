package formats.di

import formats.DefaultExportResourcesUseCase
import formats.DefaultImportResourcesUseCase
import formats.ExportResourcesUseCase
import formats.ImportResourcesUseCase
import formats.android.DefaultExportAndroidResourcesUseCase
import formats.android.DefaultParseAndroidResourcesUseCase
import formats.android.ExportAndroidResourcesUseCase
import formats.android.ParseAndroidResourcesUseCase
import formats.ios.DefaultExportIosResourcesUseCase
import formats.ios.DefaultParseIosResourcesUseCase
import formats.ios.ExportIosResourcesUseCase
import formats.ios.ParseIosResourcesUseCase
import formats.po.DefaultExportPoResourcesUseCase
import formats.po.DefaultParsePoUseCase
import formats.po.ExportPoUseCase
import formats.po.ParsePoUseCase
import formats.resx.DefaultExportResxUseCase
import formats.resx.DefaultParseResxUseCase
import formats.resx.ExportResxUseCase
import formats.resx.ParseResxUseCase
import org.koin.dsl.module

val androidModule = module {
    single<ParseAndroidResourcesUseCase> {
        DefaultParseAndroidResourcesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportAndroidResourcesUseCase> {
        DefaultExportAndroidResourcesUseCase(
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
    single<ImportResourcesUseCase> {
        DefaultImportResourcesUseCase(
            parseAndroid = get(),
            parseIos = get(),
            parseResx = get(),
            parsePo = get(),
        )
    }
    single<ExportResourcesUseCase> {
        DefaultExportResourcesUseCase(
            exportAndroid = get(),
            exportIos = get(),
            exportResx = get(),
            exportPo = get(),
        )
    }
}
