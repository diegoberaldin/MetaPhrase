package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

interface ExportGlossaryUseCase {
    suspend operator fun invoke(path: String)
}
