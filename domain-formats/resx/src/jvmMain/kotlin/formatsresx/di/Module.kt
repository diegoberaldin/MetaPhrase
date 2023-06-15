package formatsresx.di

import formatsresx.usecase.DefaultExportResxUseCase
import formatsresx.usecase.DefaultParseResxUseCase
import formatsresx.usecase.ExportResxUseCase
import formatsresx.usecase.ParseResxUseCase
import org.koin.dsl.module

val resxModule = module {
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
