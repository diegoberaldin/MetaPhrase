package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.utils.Constants
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext

internal class DefaultValidatePlaceholdersUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ValidatePlaceholdersUseCase {

    override suspend operator fun invoke(pairs: List<Pair<SegmentModel, SegmentModel>>): ValidatePlaceholdersUseCase.Output =
        withContext(dispatchers.io) {
            val invalidKeys = mutableListOf<String>()

            for ((source, target) in pairs) {
                val sourcePlaceholders = extractPlaceholders(source.text)
                val targetPlaceholders = extractPlaceholders(target.text)
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

    private fun extractPlaceholders(message: String): List<String> {
        val res = Constants.PlaceholderRegex.findAll(message).map { it.value }.toList()
        return res + Constants.NamedPlaceholderRegex.findAll(message).map { it.value }.toList()
    }
}
