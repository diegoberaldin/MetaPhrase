package language.usecase

import data.LanguageModel
import language.repo.FlagsRepository
import language.repo.LanguageNameRepository

class GetCompleteLanguageUseCase(
    private val languageNameRepository: LanguageNameRepository,
    private val flagsRepository: FlagsRepository,
) {

    operator fun invoke(lang: LanguageModel): LanguageModel {
        val name = buildString {
            val code = lang.code
            append(flagsRepository.getFlag(code))
            append(" ")
            append(languageNameRepository.getName(code))
        }
        return lang.copy(name = name)
    }
}
