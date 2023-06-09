package spellcheck.usecase

import common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import spellcheck.spelling.Spelling

class DefaultValidateSpellingUseCase(
    private val spelling: Spelling,
    private val dispatchers: CoroutineDispatcherProvider,
) : ValidateSpellingUseCase {
    override suspend fun invoke(input: List<Pair<String, String>>, lang: String): Map<String, List<String>> {
        return withContext(dispatchers.io) {
            spelling.setLanguage(lang)

            val result = mutableMapOf<String, List<String>>()

            for ((key, message) in input) {
                val sanitizedMessage = message.replace("\\n", "  ")
                val corrections = spelling.checkSentence(sanitizedMessage)
                if (corrections.isNotEmpty()) {
                    val mistakenWords = corrections.map { it.value }
                    result[key] = mistakenWords
                }
            }

            result
        }
    }
}
