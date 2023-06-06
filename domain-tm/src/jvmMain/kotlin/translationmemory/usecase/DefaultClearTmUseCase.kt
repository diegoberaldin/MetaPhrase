package translationmemory.usecase

import translationmemory.repo.MemoryEntryRepository

internal class DefaultClearTmUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) : ClearTmUseCase {
    override suspend operator fun invoke() {
        memoryEntryRepository.deleteAll()
    }
}
