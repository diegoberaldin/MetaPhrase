package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contracf for the export resources use case.
 */
interface ExportResourcesUseCase {
    /**
     * Export a list of segments as a resouce file in a given fromat.
     *
     * @param segments Segments to export
     * @param path Destination path
     * @param lang Language code
     * @param type Resource type
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String, type: ResourceFileType)
}
