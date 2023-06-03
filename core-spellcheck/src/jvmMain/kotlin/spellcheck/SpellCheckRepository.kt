package spellcheck

interface SpellCheckRepository {
    fun setLanguage(code: String)
    suspend fun check(message: String): List<SpellCheckCorrection>
}
