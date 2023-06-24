package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse Android resources use case.
 */
interface ParsePoUseCase {
    /**
     * Deserialize a list of messages from the Gettext PO format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
