package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the parse Android resources use case.
 */
interface ParsePropertiesUseCase {
    /**
     * Deserialize a list of messages from the Java properties format.
     *
     * @param path source path
     * @return list of messages
     */
    suspend operator fun invoke(path: String): List<SegmentModel>
}
