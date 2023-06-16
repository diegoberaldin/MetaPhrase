package com.github.diegoberaldin.metaphrase.core.localization

interface Localization {
    fun get(key: String): String

    fun setLanguage(lang: String)

    fun getLanguage(): String
}