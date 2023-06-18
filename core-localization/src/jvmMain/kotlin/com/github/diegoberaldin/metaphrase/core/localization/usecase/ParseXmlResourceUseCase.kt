package com.github.diegoberaldin.metaphrase.core.localization.usecase

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import java.io.InputStream

internal interface ParseXmlResourceUseCase {
    operator fun invoke(inputStream: InputStream): List<LocalizableString>
}