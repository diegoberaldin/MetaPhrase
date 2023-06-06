package repository.usecase

interface ExportTmxUseCase {
    suspend operator fun invoke(projectId: Int, path: String)
}

