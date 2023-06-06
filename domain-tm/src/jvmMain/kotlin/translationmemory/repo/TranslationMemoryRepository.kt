package translationmemory.repo

import data.SegmentModel
import data.TranslationUnit

interface TranslationMemoryRepository {
    suspend fun getSimilarities(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float = 0.75f,
    ): List<TranslationUnit>
}
