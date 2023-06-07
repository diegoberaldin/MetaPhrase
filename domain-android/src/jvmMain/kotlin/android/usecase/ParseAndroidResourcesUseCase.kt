package android.usecase

import data.SegmentModel

interface ParseAndroidResourcesUseCase {
    suspend operator fun invoke(path: String): List<SegmentModel>
}
