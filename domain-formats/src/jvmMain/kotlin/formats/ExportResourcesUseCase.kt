package formats

import projectdata.ResourceFileType
import projectdata.SegmentModel

interface ExportResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String, lang: String, type: ResourceFileType)
}
