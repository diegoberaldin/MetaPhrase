package android.usecase

import data.SegmentModel

interface ExportAndroidResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
