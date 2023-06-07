package repository.usecase

import common.coroutines.CoroutineDispatcherProvider
import data.Constants
import data.SegmentModel
import kotlinx.coroutines.withContext

internal class DefaultValidatePlaceholdersUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ValidatePlaceholdersUseCase {

    override suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): ValidatePlaceholdersUseCase.Output =
        withContext(dispatchers.io) {
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
