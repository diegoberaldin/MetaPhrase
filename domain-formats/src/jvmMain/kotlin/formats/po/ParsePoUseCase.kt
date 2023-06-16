package formats.po

import projectdata.SegmentModel

interface ParsePoUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
