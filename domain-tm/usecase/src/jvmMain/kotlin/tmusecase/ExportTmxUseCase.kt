package tmusecase

interface ExportTmxUseCase {
    suspend operator fun invoke(projectId: Int, path: String)
}

