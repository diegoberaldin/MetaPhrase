package language.repo

interface LanguageNameRepository {
    fun getName(code: String): String
}
