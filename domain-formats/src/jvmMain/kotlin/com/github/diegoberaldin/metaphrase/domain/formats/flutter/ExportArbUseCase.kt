package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export ARB resources use case.
 */
interface ExportArbUseCase {
    /**
     * Serialize the list of segments into the ARB format and save it to a destination.
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
