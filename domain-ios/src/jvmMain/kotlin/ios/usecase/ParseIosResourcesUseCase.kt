package ios.usecase

import data.SegmentModel

interface ParseIosResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}

