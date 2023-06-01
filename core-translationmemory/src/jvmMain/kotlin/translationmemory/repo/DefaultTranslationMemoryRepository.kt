package translationmemory.repo

import data.SegmentModel
import data.TranslationUnit

internal class DefaultTranslationMemoryRepository(
    private val projectSource: ProjectTranslationUnitSource,
    private val memorySource: MemoryTranslationUnitSource,
) : TranslationMemoryRepository {
    override suspend fun getSimilarities(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float,
    ): List<TranslationUnit> {
        val unitsFromMemory = memorySource.getUnits(
            projectId = projectId,
            key = segment.key,
            threshold = threshold,
            languageId = languageId,
        ).distinctBy { it.original?.text.orEmpty() }

        val unitsFromProject = projectSource.getUnits(
            projectId = projectId,
            key = segment.key,
            threshold = threshold,
            languageId = languageId,
        ).filter {
            val original = it.original?.text.orEmpty()
            unitsFromMemory.none { m -> m.original?.text.orEmpty() == original }
        }

        return unitsFromMemory + unitsFromProject
    }
}
