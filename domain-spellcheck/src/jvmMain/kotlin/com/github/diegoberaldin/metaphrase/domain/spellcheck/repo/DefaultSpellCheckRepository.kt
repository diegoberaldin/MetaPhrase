package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection
import com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling.Spelling
import kotlinx.coroutines.withContext

internal class DefaultSpellCheckRepository(
    private val spelling: Spelling,
    private val dispatchers: CoroutineDispatcherProvider,
) : SpellCheckRepository {

    override suspend fun setLanguage(code: String) {
        spelling.setLanguage(code)
    }

    override suspend fun check(message: String): List<SpellCheckCorrection> {
        if (!spelling.isInitialized) return emptyList()

        return withContext(dispatchers.io) {
            val sanitizedMessage = message.replace("\\n", "  ")
            spelling.checkSentence(sanitizedMessage)
        }
    }

    override suspend fun addUserDefineWord(word: String) {
        if (spelling.isInitialized) {
            spelling.addUserDefinedWord(word)
        }
    }
}
