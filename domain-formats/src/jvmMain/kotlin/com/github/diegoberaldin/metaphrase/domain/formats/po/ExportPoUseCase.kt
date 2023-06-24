package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export PO resources use case.
 */
interface ExportPoUseCase {
    /**
     * Serialize the list of segments into the Gettext PO format and save it to a destination.
     *
     *
     * @param segments Segments to be exported
     * @param path Destination path
     * @param lang language of the message to include in the PO header
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String)
}
