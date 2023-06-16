package formats

import projectdata.ResourceFileType
import projectdata.SegmentModel

interface ImportResourcesUseCase {
    suspend operator fun invoke(path: String, type: ResourceFileType): List<SegmentModel>
}
