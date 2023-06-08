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
            spelling.checkSentence(sanitizedMessage)
        }
    }
}
