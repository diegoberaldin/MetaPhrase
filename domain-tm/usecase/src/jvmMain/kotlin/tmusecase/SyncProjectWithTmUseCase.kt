package tmusecase

interface SyncProjectWithTmUseCase {
    suspend operator fun invoke(projectId: Int)
}
