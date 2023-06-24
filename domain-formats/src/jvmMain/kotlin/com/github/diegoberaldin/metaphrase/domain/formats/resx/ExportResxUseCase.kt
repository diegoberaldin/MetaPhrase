package com.github.diegoberaldin.metaphrase.domain.formats.resx

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export resx resources use case.
 */
interface ExportResxUseCase {
    /**
     * Serialize the list of segments into Windows resx format and save it to a destination.
     *
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
