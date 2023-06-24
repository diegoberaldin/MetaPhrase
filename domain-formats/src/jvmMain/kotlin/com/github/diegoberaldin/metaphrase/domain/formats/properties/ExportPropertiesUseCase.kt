package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export properties resources use case.
 */
interface ExportPropertiesUseCase {
    /**
     * Serialize the list of segments into Java properties format and save it to a destination.
     *
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
