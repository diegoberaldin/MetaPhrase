package com.github.diegoberaldin.metaphrase.core.localization.usecase

import com.github.diegoberaldin.metaphrase.core.localization.data.LocalizableString
import java.io.InputStream

/**
 * Contract for the parse resource use case.
 */
internal interface ParseResourceUseCase {
    /**
     * Load the app messages from an input stream.
     *
     * @param inputStream Input stream to read
     * @param lang Language code
     * @return list of message with key and translations
     */
    operator fun invoke(inputStream: InputStream, lang: String): List<LocalizableString>
}
