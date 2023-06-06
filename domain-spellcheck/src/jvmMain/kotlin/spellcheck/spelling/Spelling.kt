package spellcheck.spelling

interface Spelling {

    val isInitialized: Boolean
    fun setLanguage(code: String)
    fun check(word: String): Pair<Boolean, List<String>>
}
