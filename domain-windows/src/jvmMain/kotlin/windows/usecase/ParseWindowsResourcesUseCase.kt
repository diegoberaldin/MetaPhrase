package windows.usecase

import projectdata.SegmentModel

interface ParseWindowsResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
