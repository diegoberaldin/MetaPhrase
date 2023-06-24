package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse ARB resources use case.
 */
interface ParseArbUseCase {
    /**
     * Deserialize a list of messages from the Flutter ARB format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
