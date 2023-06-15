package spellcheck.usecase

import common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import spellcheck.repo.SpellCheckRepository

class DefaultValidateSpellingUseCase(
    private val repository: SpellCheckRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ValidateSpellingUseCase {
    override suspend fun invoke(
        input: List<ValidateSpellingUseCase.InputItem>,
        lang: String,
    ): Map<String, List<String>> {
        return withContext(dispatchers.io) {
            repository.setLanguage(lang)

            val result = mutableMapOf<String, List<String>>()

            for ((key, message) in input) {
                val sanitizedMessage = message.replace("\\n", "  ")
                val corrections = repository.check(sanitizedMessage)
                if (corrections.isNotEmpty()) {
                    val mistakenWords = corrections.map { it.value }
                    result[key] = mistakenWords
                }
            }

            result
        }
    }
}
