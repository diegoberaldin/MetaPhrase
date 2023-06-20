package com.github.diegoberaldin.metaphrase.domain.tm.usecase

interface ExportTmxUseCase {
    suspend operator fun invoke(sourceLang: String, path: String)
}

