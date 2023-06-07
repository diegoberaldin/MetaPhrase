package spellcheck.spelling

import pt.tumba.spell.DefaultWordFinder
import pt.tumba.spell.SpellChecker
import spellcheck.repo.DefaultSpellCheckRepository
import java.io.InputStreamReader
import java.io.Reader

class DefaultSpelling : Spelling {

    companion object {
        private val SUPPORTED_LANGUAGES = listOf("en", "es", "it", "pt")

        fun String.getDictionary(): Reader? = DefaultSpellCheckRepository::class.java.classLoader
            .getResourceAsStream("dictionaries/$this.txt")
            ?.let { InputStreamReader(it) }
    }

    override var isInitialized = false

    private val spellChecker = SpellChecker()

    override fun setLanguage(code: String) {
        if (code in SUPPORTED_LANGUAGES) {
            runCatching {
                val reader = code.getDictionary()
                if (reader != null) {
                    spellChecker.initialize(reader)
                    isInitialized = true
                }
            }
        } else {
            isInitialized = false
        }
    }

    override fun check(word: String): Pair<Boolean, List<String>> {
        val finder = DefaultWordFinder(word)
        val aux = finder.next() ?: return true to emptyList()
        val aux2 = spellChecker.findMostSimilar(aux)
        return when {
            aux2 == null -> {
                // misspelled word with no suggestions
                false to emptyList()
            }

            !aux2.equals(aux, ignoreCase = true) -> {
                // misspelled word with suggestions
                val suggestions = spellChecker.findMostSimilarList(aux)
                false to buildList {
                    for (e in suggestions) {
                        (e as? String)?.also {
                            this += it
                        }
                    }
                }
            }

            else -> true to emptyList() // correct word
        }
    }
}
