package ios.usecase

import projectdata.SegmentModel

interface ExportIosResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
