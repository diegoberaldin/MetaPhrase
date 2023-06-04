package spellcheck

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pt.tumba.spell.DefaultWordFinder
import pt.tumba.spell.SpellChecker
import java.io.InputStreamReader
import java.io.Reader

internal class DefaultSpellCheckRepository : SpellCheckRepository {
    companion object {
        private val SUPPORTED_LANGUAGES = listOf("en", "es", "it", "pt")

        fun String.getDictionary(): Reader? = DefaultSpellCheckRepository::class.java.classLoader
            .getResourceAsStream("dictionaries/$this.txt")
            ?.let { InputStreamReader(it) }
    }

    private var initialized = false
    private val spellChecker = SpellChecker()

    override fun setLanguage(code: String) {
        if (code in SUPPORTED_LANGUAGES) {
            runCatching {
                val reader = code.getDictionary()
                if (reader != null) {
                    spellChecker.initialize(reader)
                    initialized = true
                }
            }
        } else {
            initialized = false
        }
    }

    override suspend fun check(message: String): List<SpellCheckCorrection> {
        if (!initialized) return emptyList()

        return withContext(Dispatchers.IO) {
            val sanitizedMessage = message.replace("\\n", "  ")
            val res = mutableListOf<SpellCheckCorrection>()
            val separator = Regex("\\W+")
            var startIdx = 0
            for (word in sanitizedMessage.split(separator)) {
                val match = runCatching {
                    Regex(word).find(sanitizedMessage, startIdx)
                }.getOrNull() ?: continue

                val finder = DefaultWordFinder(word)
                finder.next()?.also { aux ->
                    val aux2 = spellChecker.findMostSimilar(aux)
                    when {
                        aux2 == null -> {
                            // misspelled word with no suggestions
                            res += SpellCheckCorrection(
                                indices = match.range,
                                value = word,
                            )
                        }

                        !aux2.equals(aux, ignoreCase = true) -> {
                            // misspelled word with suggestions
                            val suggestions = spellChecker.findMostSimilarList(aux)
                            res += SpellCheckCorrection(
                                indices = match.range,
                                value = word,
                                suggestions = buildList {
                                    for (e in suggestions) {
                                        (e as? String)?.also {
                                            this += it
                                        }
                                    }
                                },
                            )
                        }

                        else -> Unit // correct word
                    }
                }

                startIdx = match.range.last
            }
            res
        }
    }
}
