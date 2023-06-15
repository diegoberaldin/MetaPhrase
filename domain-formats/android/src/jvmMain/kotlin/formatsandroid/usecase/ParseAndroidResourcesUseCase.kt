package formatsandroid.usecase

import projectdata.SegmentModel

interface ParseAndroidResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
