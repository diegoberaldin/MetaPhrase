package projectusecase

import projectdata.LanguageModel
import projectdata.SegmentModel

interface ImportSegmentsUseCase {
    suspend operator fun invoke(
        segments: List<SegmentModel>,
        language: LanguageModel,
        projectId: Int,
    )
}
