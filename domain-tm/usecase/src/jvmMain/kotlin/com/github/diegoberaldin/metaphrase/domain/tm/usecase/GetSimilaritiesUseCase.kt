package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

/**
 * Contract for the get similarities use case.
 */
interface GetSimilaritiesUseCase {
    /**
     * Get a series of matches from the translation memory or the current project for a given segment.
     *
     * @param segment source message
     * @param projectId Project ID
     * @param languageId Language ID
     * @param threshold minimum similarity threshold
     * @return list of matching translation units
     */
    suspend operator fun invoke(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float = 0.75f,
    ): List<TranslationUnit>
}
