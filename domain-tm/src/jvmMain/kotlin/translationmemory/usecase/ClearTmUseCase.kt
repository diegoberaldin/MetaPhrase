package translationmemory.usecase

import translationmemory.repo.MemoryEntryRepository


class ClearTmUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) {
    suspend operator fun invoke() {
        memoryEntryRepository.deleteAll()
    }
}
