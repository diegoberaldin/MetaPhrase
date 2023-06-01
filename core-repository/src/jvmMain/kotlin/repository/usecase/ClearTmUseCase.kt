package repository.usecase

import repository.local.MemoryEntryRepository

class ClearTmUseCase(
    private val memoryEntryRepository: MemoryEntryRepository,
) {
    suspend operator fun invoke() {
        memoryEntryRepository.deleteAll()
    }
}
