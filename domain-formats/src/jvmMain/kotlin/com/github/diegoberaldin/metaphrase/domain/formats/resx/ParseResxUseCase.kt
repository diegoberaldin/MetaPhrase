package com.github.diegoberaldin.metaphrase.domain.formats.resx

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse Android resources use case.
 */
interface ParseResxUseCase {
    /**
     * Deserialize a list of messages from the Windows resx format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
