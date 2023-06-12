package tmusecase

interface ImportTmxUseCase {
    suspend operator fun invoke(path: String)
}

