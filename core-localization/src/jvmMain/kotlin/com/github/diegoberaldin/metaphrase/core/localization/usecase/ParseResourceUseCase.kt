package com.github.diegoberaldin.metaphrase.core.localization.usecase

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import java.io.InputStream

internal interface ParseResourceUseCase {
    operator fun invoke(inputStream: InputStream, lang: String): List<LocalizableString>
}
