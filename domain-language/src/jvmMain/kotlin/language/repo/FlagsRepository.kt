package language.repo

interface FlagsRepository {
    fun getFlag(code: String): String
}
