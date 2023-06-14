package glossaryusecase

import common.coroutines.CoroutineDispatcherProvider
import glossarydata.GlossaryTermModel
import glossaryrepository.GlossaryTermRepository
import kotlinx.coroutines.withContext
import spellcheck.spelling.Spelling

internal class DefaultGetGlossaryTermsUseCase(
    private val repository: GlossaryTermRepository,
    private val dispatchers: CoroutineDispatcherProvider,
    private val spelling: Spelling,
) : GetGlossaryTermsUseCase {
    override suspend fun invoke(message: String, lang: String): List<GlossaryTermModel> {
        return withContext(dispatchers.io) {
            val sanitizedMessage = message.replace("\\n", "  ")
            val res = mutableSetOf<GlossaryTermModel>()
            spelling.setLanguage(lang)
            val lemmata = spelling.getLemmata(sanitizedMessage).takeIf { it.isNotEmpty() }
                ?: defaultTokenization(sanitizedMessage)
            for (word in lemmata) {
                val term = repository.get(lemma = word.lowercase(), lang = lang) ?: continue
                res += term
            }
            res.toList()
        }
    }

    private fun defaultTokenization(message: String): List<String> {
        val res = mutableListOf<String>()
        val separator = Regex("\\W+")
        var startIdx = 0
        for (word in message.split(separator)) {
            val match = runCatching {
                Regex(word).find(message, startIdx)
            }.getOrNull() ?: continue

            res += word

            startIdx = match.range.last
        }
        return res
    }
}
