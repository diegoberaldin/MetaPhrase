package repository.usecase

import data.Constants
import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultValidatePlaceholdersUseCase : ValidatePlaceholdersUseCase {

    override suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): ValidatePlaceholdersUseCase.Output =
        withContext(Dispatchers.IO) {
            val invalidKeys = mutableListOf<String>()

            for ((source, target) in pairs) {
                val sourcePlaceholders = Constants.PlaceholderRegex.findAll(source.text).map { it.value }
                val targetPlaceholders = Constants.PlaceholderRegex.findAll(target.text).map { it.value }
                val exceeding = (targetPlaceholders - sourcePlaceholders.toSet()).toList()
                val missing = (sourcePlaceholders - targetPlaceholders.toSet()).toList()
                if (missing.isNotEmpty() || exceeding.isNotEmpty()) {
                    invalidKeys += source.key
                }
            }

            if (invalidKeys.isEmpty()) {
                ValidatePlaceholdersUseCase.Output.Valid
            } else {
                ValidatePlaceholdersUseCase.Output.Invalid(keys = invalidKeys)
            }
        }
}
