package com.github.diegoberaldin.metaphrase.domain.formats.android

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract for the export Android resources use case.
 */
interface ExportAndroidResourcesUseCase {
    /**
     * Serialize the list of segments into the Android XML format and save it to a destination.
     *
     * @param segments Segments to be exported
     * @param path Destination path
     */
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
