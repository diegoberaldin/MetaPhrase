package com.github.diegoberaldin.metaphrase.domain.formats.ios

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse iOS resources use case.
 */
interface ParseIosResourcesUseCase {
    /**
     * Deserialize a list of messages from the iOS stringtable format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
