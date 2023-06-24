package com.github.diegoberaldin.metaphrase.domain.language.data

/**
 * Language model.
 *
 * @property id Language ID
 * @property code Language code (two letters ISO 693-1)
 * @property name Language readable name
 * @property isBase indicates this is the source langauge of a project
 * @constructor Create [LanguageModel]
 */
data class LanguageModel(
    val id: Int = 0,
    val code: String = "",
    val name: String = "",
    val isBase: Boolean = false,
)
