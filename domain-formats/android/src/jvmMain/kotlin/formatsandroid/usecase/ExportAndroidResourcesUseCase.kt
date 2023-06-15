package formatsandroid.usecase

import projectdata.SegmentModel

interface ExportAndroidResourcesUseCase {
    suspend operator fun invoke(segments: List<SegmentModel>, path: String)
}
