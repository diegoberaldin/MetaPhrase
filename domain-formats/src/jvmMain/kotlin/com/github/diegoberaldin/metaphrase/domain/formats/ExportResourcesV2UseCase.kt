package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contracf for the export resources use case.
 */
interface ExportResourcesV2UseCase {
    /**
     * Export a list of segments as a resouce file in a given fromat.
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

