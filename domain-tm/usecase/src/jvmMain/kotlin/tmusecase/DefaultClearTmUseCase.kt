package tmusecase

import tmrepository.MemoryEntryRepository

internal class DefaultClearTmUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) : ClearTmUseCase {
    override suspend operator fun invoke() {
        memoryEntryRepository.deleteAll()
    }
}
