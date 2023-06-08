package spellcheck.spelling

import org.languagetool.JLanguageTool
import org.languagetool.Language
import org.languagetool.language.AmericanEnglish
import org.languagetool.language.Arabic
import org.languagetool.language.Catalan
import org.languagetool.language.Chinese
import org.languagetool.language.Dutch
import org.languagetool.language.Esperanto
import org.languagetool.language.French
import org.languagetool.language.Galician
import org.languagetool.language.GermanyGerman
import org.languagetool.language.Greek
import org.languagetool.language.Irish
import org.languagetool.language.Italian
import org.languagetool.language.Japanese
import org.languagetool.language.Persian
import org.languagetool.language.Polish
import org.languagetool.language.Portuguese
import org.languagetool.language.Romanian
import org.languagetool.language.Russian
import org.languagetool.language.Slovak
import org.languagetool.language.Spanish
import org.languagetool.language.Ukrainian
import spellcheck.SpellCheckCorrection

class DefaultSpelling : Spelling {

    companion object {
        private fun String.toLanguage(): Language? = when (this) {
            "en" -> AmericanEnglish()
            "pt" -> Portuguese()
            "ar" -> Arabic()
            "nl" -> Dutch()
            "ca" -> Catalan()
            "zh" -> Chinese()
            "eo" -> Esperanto()
            "fr" -> French()
            "gl" -> Galician()
            "de" -> GermanyGerman()
            "el" -> Greek()
            "ga" -> Irish()
            "it" -> Italian()
            "ja" -> Japanese()
            "fa" -> Persian()
            "pl" -> Polish()
            "ro" -> Romanian()
            "ru" -> Russian()
            "sk" -> Slovak()
            "es" -> Spanish()
            "uk" -> Ukrainian()
            else -> null
        }
    }

    override val isInitialized: Boolean
        get() = ::languageTool.isInitialized

    private lateinit var languageTool: JLanguageTool

    override fun setLanguage(code: String) {
        languageTool = JLanguageTool(code.toLanguage())
    }

    override fun check(word: String): Pair<Boolean, List<String>> {
        val defaultResult: Pair<Boolean, List<String>> = true to emptyList()
        if (!isInitialized) return defaultResult

        return try {
            val matches = languageTool.check(word)
            val suggestions = matches.flatMap { it.suggestedReplacements }
            if (suggestions.isEmpty()) {
                return defaultResult
            }

            false to suggestions
        } catch (e: Throwable) {
            e.printStackTrace()
            defaultResult
        }
    }

    override fun checkSentence(message: String): List<SpellCheckCorrection> {
        val defaultResult: List<SpellCheckCorrection> = emptyList()
        if (!isInitialized) return defaultResult

        return try {
            val matches = languageTool.check(message)
            matches.map {
                val range = it.fromPos until it.toPos
                SpellCheckCorrection(
                    indices = range,
                    value = message.substring(range = range),
                    suggestions = it.suggestedReplacements,
                )
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            defaultResult
        }
    }
}
