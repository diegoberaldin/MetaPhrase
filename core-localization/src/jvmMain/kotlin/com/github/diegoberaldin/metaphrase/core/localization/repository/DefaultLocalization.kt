package com.github.diegoberaldin.metaphrase.core.localization.repository

import LocalizationResourceLoader
import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import com.github.diegoberaldin.metaphrase.core.localization.localized
import com.github.diegoberaldin.metaphrase.core.localization.usecase.ParseXmlResourceUseCase
import java.io.InputStream

internal class DefaultLocalization(
    private val parseResource: ParseXmlResourceUseCase,
) : Localization {

    private val defaultValues: List<LocalizableString> = LocalizationResourceLoader.loadAsStream("en")?.use {
        load(it)
    } ?: emptyList()
    private var localizables: List<LocalizableString> = emptyList()

    override fun setLanguage(lang: String) {
        localizables = LocalizationResourceLoader.loadAsStream(lang)?.use {
            load(it)
        } ?: defaultValues
    }

    override fun getLanguage(): String = "lang".localized()

    override fun get(key: String) = localizables.firstOrNull { it.key == key }?.value
        ?: defaultValues.firstOrNull { it.key == key }?.value
        ?: key

    private fun load(inputStream: InputStream): List<LocalizableString> = parseResource(inputStream)
}
