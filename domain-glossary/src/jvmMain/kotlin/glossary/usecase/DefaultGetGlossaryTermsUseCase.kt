package glossary.usecase

import common.coroutines.CoroutineDispatcherProvider
import data.GlossaryTermModel
import glossary.repo.GlossaryTermRepository
import kotlinx.coroutines.withContext

internal class DefaultGetGlossaryTermsUseCase(
    private val repository: GlossaryTermRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : GetGlossaryTermsUseCase {
    override suspend fun invoke(message: String, lang: String): List<GlossaryTermModel> {
        return withContext(dispatchers.io) {
            val sanitizedMessage = message.replace("\\n", "  ")
            val res = mutableListOf<GlossaryTermModel>()
            val separator = Regex("\\W+")
            var startIdx = 0
            for (word in sanitizedMessage.split(separator)) {
                val match = runCatching {
                    Regex(word).find(sanitizedMessage, startIdx)
                }.getOrNull() ?: continue

                val term = repository.get(lemma = word, lang = lang)
                if (term != null) {
                    res += term
                }

                startIdx = match.range.last
            }
            res
        }
    }
}
