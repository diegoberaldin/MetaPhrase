package repository.usecase

import data.InvalidPlaceholderReferenceModel
import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidatePlaceholdersUseCase {

    sealed interface Output {
        object Valid : Output

        data class Invalid(
            val references: List<InvalidPlaceholderReferenceModel> = emptyList()
        ) : Output
    }

    suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): Output = withContext(Dispatchers.IO) {
        Output.Valid
    }
}