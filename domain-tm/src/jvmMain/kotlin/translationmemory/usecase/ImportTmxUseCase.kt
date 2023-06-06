package translationmemory.usecase

interface ImportTmxUseCase {
    suspend operator fun invoke(path: String)
}

