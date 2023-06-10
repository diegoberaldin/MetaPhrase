package translationmemory.usecase

interface SyncProjectWithTmUseCase {
    suspend operator fun invoke(projectId: Int)
}
