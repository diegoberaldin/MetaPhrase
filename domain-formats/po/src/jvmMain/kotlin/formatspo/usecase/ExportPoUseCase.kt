package formatspo.usecase

import projectdata.SegmentModel

interface ExportPoUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String)
}
