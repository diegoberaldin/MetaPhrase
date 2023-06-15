package formatsresx.usecase

import projectdata.SegmentModel

interface ExportResxUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
