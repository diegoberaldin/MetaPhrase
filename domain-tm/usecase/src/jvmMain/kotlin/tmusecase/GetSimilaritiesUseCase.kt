package tmusecase

import projectdata.SegmentModel
import projectdata.TranslationUnit

interface GetSimilaritiesUseCase {
    suspend operator fun invoke(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float = 0.75f,
    ): List<TranslationUnit>
}
