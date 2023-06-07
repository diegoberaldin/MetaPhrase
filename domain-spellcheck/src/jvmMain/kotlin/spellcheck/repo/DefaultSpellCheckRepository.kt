package spellcheck.repo

import common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import spellcheck.SpellCheckCorrection
import spellcheck.spelling.Spelling

internal class DefaultSpellCheckRepository(
    private val spelling: Spelling,
    private val dispatchers: CoroutineDispatcherProvider,
) : SpellCheckRepository {

    override fun setLanguage(code: String) {
        spelling.setLanguage(code)
    }

    override suspend fun check(message: String): List<SpellCheckCorrection> {
        if (!spelling.isInitialized) return emptyList()

        return withContext(dispatchers.io) {
            val sanitizedMessage = message.replace("\\n", "  ")
            val res = mutableListOf<SpellCheckCorrection>()
            val separator = Regex("\\W+")
            var startIdx = 0
            for (word in sanitizedMessage.split(separator)) {
                val match = runCatching {
                    Regex(word).find(sanitizedMessage, startIdx)
                }.getOrNull() ?: continue

                val (isCorrect, suggestions) = spelling.check(word)
                if (!isCorrect) {
                    res += SpellCheckCorrection(
                        value = word,
                        indices = match.range,
                        suggestions = suggestions,
                    )
                }

                startIdx = match.range.last
            }
            res
        }
    }
}
