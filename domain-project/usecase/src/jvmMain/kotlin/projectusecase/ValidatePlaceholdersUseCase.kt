package projectusecase

import data.SegmentModel

interface ValidatePlaceholdersUseCase {
    sealed interface Output {
        object Valid : Output

        data class Invalid(
            val keys: List<String> = emptyList(),
        ) : Output
    }

    suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): Output
}
