package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository

class DefaultClearGlossaryUseCase(
    private val repository: GlossaryTermRepository,
) : ClearGlossaryUseCase {
    override suspend fun invoke() {
        repository.deleteAll()
    }
}
