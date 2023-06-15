package windows.usecase

import projectdata.SegmentModel

interface ExportWindowsResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
