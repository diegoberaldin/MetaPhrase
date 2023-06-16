package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

interface ImportGlossaryUseCase {
    suspend operator fun invoke(path: String)
}
