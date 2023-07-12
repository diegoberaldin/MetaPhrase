package com.github.diegoberaldin.metaphrase.core.localization.repository

import LocalizationResourceLoader
import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.core.localization.usecase.ParseResourceUseCase

internal class DefaultLocalization(
    private val parseResource: ParseResourceUseCase,
) : Localization {

    private val defaultValues: List<LocalizableString> = LocalizationResourceLoader.loadAsStream("en")?.use {
        parseResource(inputStream = it, lang = "en")
    } ?: emptyList()
    private var localizables: List<LocalizableString> = emptyList()

    override fun setLanguage(lang: String) {
        localizables = LocalizationResourceLoader.loadAsStream(lang)?.use {
            parseResource(inputStream = it, lang = lang)
        } ?: defaultValues
    }

    override fun getLanguage(): String = "lang".localized()

    override fun get(key: String) = localizables.firstOrNull {
        it.key == key
    }?.value.takeIf { !it.isNullOrEmpty() }
        ?: defaultValues.firstOrNull { it.key == key }?.value
        ?: key
}
