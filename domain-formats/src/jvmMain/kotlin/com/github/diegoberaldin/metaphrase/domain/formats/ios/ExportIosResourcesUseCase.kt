package com.github.diegoberaldin.metaphrase.domain.formats.ios

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export iOS resources use case.
 */
interface ExportIosResourcesUseCase {
    /**
     * Serialize the list of segments into the stringtable format and save it to a destination.
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
