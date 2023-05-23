package repository.usecase

import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ValidatePlaceholdersUseCase {

    companion object {
        private val PLACEHOLDER_REGEX = Regex("%(?:\\d+\\\$)?[dfsu]")
    }

    sealed interface Output {
        object Valid : Output

        data class Invalid(
            val keys: List<String> = emptyList()
        ) : Output
    }

    suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): Output = withContext(Dispatchers.IO) {
        val invalidKeys = mutableListOf<String>()

        for ((source, target) in pairs) {
            val sourcePlaceholders = PLACEHOLDER_REGEX.findAll(source.text).map { it.value }
            val targetPlaceholders = PLACEHOLDER_REGEX.findAll(target.text).map { it.value }
            val exceeding = (targetPlaceholders - sourcePlaceholders.toSet()).toList()
            val missing = (sourcePlaceholders - targetPlaceholders.toSet()).toList()
            if (missing.isNotEmpty() || exceeding.isNotEmpty()) {
                invalidKeys += source.key
            }
        }

        if (invalidKeys.isEmpty()) {
            Output.Valid
        } else {
            Output.Invalid(keys = invalidKeys)
        }

    }
}