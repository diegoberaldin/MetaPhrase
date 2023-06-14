package windows.usecase

import data.SegmentModel

interface ExportWindowsResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
