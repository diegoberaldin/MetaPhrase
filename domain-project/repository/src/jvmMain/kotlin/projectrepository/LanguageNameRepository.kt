package projectrepository

interface LanguageNameRepository {
    fun getName(code: String): String
}
