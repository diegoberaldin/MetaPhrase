package spellcheck.usecase

interface ValidateSpellingUseCase {
    suspend operator fun invoke(input: List<Pair<String, String>>, lang: String): Map<String, List<String>>
}
