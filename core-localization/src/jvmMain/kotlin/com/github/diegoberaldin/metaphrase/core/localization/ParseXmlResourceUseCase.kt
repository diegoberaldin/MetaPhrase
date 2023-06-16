package com.github.diegoberaldin.metaphrase.core.localization

import java.io.InputStream

internal interface ParseXmlResourceUseCase {
    operator fun invoke(inputStream: InputStream): List<LocalizableString>
}