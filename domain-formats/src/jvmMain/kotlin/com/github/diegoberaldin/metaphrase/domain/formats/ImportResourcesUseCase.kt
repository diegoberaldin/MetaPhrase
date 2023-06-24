package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the import resources use case.
 */
interface ImportResourcesUseCase {
    /**
     * Import the content of a resource file as a list of segments.
     *
     * @param path Source path
     * @param type Resource type
     * @return segments to be imported
     */
    suspend operator fun invoke(path: String, type: ResourceFileType): List<SegmentModel>
}
