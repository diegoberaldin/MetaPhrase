package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export resources "all-in-one" use case.
 */
interface ExportResourcesV2UseCase {
    /**
     * Export a list of segments as a resource file in a given format.
     *
     * @param data Map containing the language code as keys and the list of segments for that language
     * @param path Destination path
     * @param type Resource type
     */
    suspend operator fun invoke(
        data: Map<String, List<SegmentModel>>,
        path: String,
        type: ResourceFileType,
    )
}
