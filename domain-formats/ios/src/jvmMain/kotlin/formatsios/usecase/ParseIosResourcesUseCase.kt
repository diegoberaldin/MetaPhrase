package formatsios.usecase

import projectdata.SegmentModel

interface ParseIosResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}

