package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository

internal class DefaultClearTmUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) : ClearTmUseCase {
    override suspend operator fun invoke() {
        memoryEntryRepository.deleteAll()
    }
}
