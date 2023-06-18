package com.github.diegoberaldin.metaphrase.domain.language.data

data class LanguageModel(
    val id: Int = 0,
    val code: String = "",
    val name: String = "",
    val isBase: Boolean = false,
)
