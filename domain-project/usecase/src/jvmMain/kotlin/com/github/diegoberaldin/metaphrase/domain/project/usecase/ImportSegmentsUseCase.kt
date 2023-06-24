package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel

/**
 * Contract of the import segments use case.
 */
interface ImportSegmentsUseCase {
    /**
     * Save a list of segments for a given language inside a given project.
     *
     * @param segments Segments to save
     * @param language Language the segments belong to
     * @param projectId Project ID
     */
    suspend operator fun invoke(
        segments: List<SegmentModel>,
        language: LanguageModel,
        projectId: Int,
    )
}
