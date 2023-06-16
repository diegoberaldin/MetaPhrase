package com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase

interface ValidateSpellingUseCase {

    data class InputItem(
        val key: String,
        val message: String,
    )

    suspend operator fun invoke(input: List<InputItem>, lang: String): Map<String, List<String>>
}
