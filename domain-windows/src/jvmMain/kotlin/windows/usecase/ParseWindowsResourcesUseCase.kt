package windows.usecase

import data.SegmentModel

interface ParseWindowsResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
