package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export ngx-translate JSON resources use case.
 */
interface ExportJsonUseCase {
    /**
     * Serialize the list of segments into the ngx-translate JSON format and save it to a destination.
     *
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
