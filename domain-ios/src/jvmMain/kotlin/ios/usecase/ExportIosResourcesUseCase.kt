package ios.usecase

import data.SegmentModel

interface ExportIosResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
