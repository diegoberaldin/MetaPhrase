package com.github.diegoberaldin.metaphrase.domain.formats.di

import com.github.diegoberaldin.metaphrase.domain.formats.DefaultExportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.DefaultImportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.DefaultExportAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.DefaultParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.ExportAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.android.ParseAndroidResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.DefaultExportArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.DefaultParseArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.ExportArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.flutter.ParseArbUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.DefaultExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.DefaultParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ExportIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ios.ParseIosResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.DefaultExportJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.DefaultParseJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.ExportJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.json.ParseJsonUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.DefaultExportPoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.DefaultParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ExportPoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.po.ParsePoUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.DefaultExportPropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.DefaultParsePropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.ExportPropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.properties.ParsePropertiesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.DefaultExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.DefaultParseResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ExportResxUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.resx.ParseResxUseCase
import org.koin.dsl.module

private val androidModule = module {
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

private val iosModule = module {
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

private val poModule = module {
    single<ParsePoUseCase> {
        DefaultParsePoUseCase(
            dispatchers = get(),
        )
    }
    single<ExportPoUseCase> {
        DefaultExportPoUseCase(
            dispatchers = get(),
        )
    }
}

private val jsonModule = module {
    single<ParseJsonUseCase> {
        DefaultParseJsonUseCase(
            dispatchers = get(),
        )
    }
    single<ExportJsonUseCase> {
        DefaultExportJsonUseCase(
            dispatchers = get(),
        )
    }
}

private val flutterModule = module {
    single<ParseArbUseCase> {
        DefaultParseArbUseCase(
            dispatchers = get(),
        )
    }
    single<ExportArbUseCase> {
        DefaultExportArbUseCase(
            dispatchers = get(),
        )
    }
}

private val propertiesModule = module {
    single<ParsePropertiesUseCase> {
        DefaultParsePropertiesUseCase(
            dispatchers = get(),
        )
    }
    single<ExportPropertiesUseCase> {
        DefaultExportPropertiesUseCase(
            dispatchers = get(),
        )
    }
}

/**
 * DI module for the domain-glossary subproject.
 */
val formatsModule = module {
    includes(
        androidModule,
        iosModule,
        resxModule,
        poModule,
        jsonModule,
        flutterModule,
        propertiesModule,
    )
    single<ImportResourcesUseCase> {
        DefaultImportResourcesUseCase(
            parseAndroid = get(),
            parseIos = get(),
            parseResx = get(),
            parsePo = get(),
            parseJson = get(),
            parseArb = get(),
            parseProperties = get(),
        )
    }
    single<ExportResourcesUseCase> {
        DefaultExportResourcesUseCase(
            exportAndroid = get(),
            exportIos = get(),
            exportResx = get(),
            exportPo = get(),
            exportJson = get(),
            exportArb = get(),
            exportProperties = get(),
        )
    }
}
