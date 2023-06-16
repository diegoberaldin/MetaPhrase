package formats.resx

import projectdata.SegmentModel

interface ParseResxUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
