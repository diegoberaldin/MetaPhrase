package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse JSON resources use case.
 */
interface ParseJsonUseCase {
    /**
     * Deserialize a list of messages from the Gettext PO format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
