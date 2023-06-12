package projectusecase

import data.LanguageModel
import data.SegmentModel

interface ImportSegmentsUseCase {
    suspend operator fun invoke(
        segments: List<SegmentModel>,
        language: LanguageModel,
        projectId: Int,
    )
}
