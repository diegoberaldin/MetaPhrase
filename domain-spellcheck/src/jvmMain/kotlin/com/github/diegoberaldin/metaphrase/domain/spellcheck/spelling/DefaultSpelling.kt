package com.github.diegoberaldin.metaphrase.domain.spellcheck.spelling

import com.github.diegoberaldin.metaphrase.domain.spellcheck.SpellCheckCorrection
import com.github.diegoberaldin.metaphrase.domain.spellcheck.repo.UserDefinedWordsRepository
import org.languagetool.JLanguageTool
import org.languagetool.Language
import org.languagetool.UserConfig
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

class DefaultSpelling(
    private val userDefinedWordsRepository: UserDefinedWordsRepository,
) : Spelling {

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

    override suspend fun setLanguage(code: String) {
        val language = code.toLanguage()
        initialize(language)
    }

    private suspend fun initialize(language: Language?) {
        if (language == null) {
            return
        }
        val userDefinedWords = userDefinedWordsRepository.getAll(lang = language.locale.language)
        languageTool = JLanguageTool(
            language,
            null,
            null,
            UserConfig(userDefinedWords),
        )
    }

    override fun check(word: String): List<String> {
        if (!isInitialized) return emptyList()

        return try {
            val matches = languageTool.check(word)
            matches.flatMap { it.suggestedReplacements }
        } catch (e: Throwable) {
            e.printStackTrace()
            emptyList()
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

    override fun getLemmata(message: String): List<String> {
        if (!isInitialized) return emptyList()

        val sentence = languageTool.getAnalyzedSentence(message)
        return sentence.lemmaSet.toList()
    }

    override suspend fun addUserDefinedWord(word: String) {
        if (isInitialized) {
            val language = languageTool.language
            userDefinedWordsRepository.add(word = word, lang = language.locale.language)
            initialize(language)
        }
    }
}
