package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.MemoryTranslationUnitSource
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource.ProjectTranslationUnitSource

internal class DefaultGetSimilaritiesUseCase(
    private val projectSource: ProjectTranslationUnitSource,
    private val memorySource: MemoryTranslationUnitSource,
) : GetSimilaritiesUseCase {
    override suspend fun invoke(
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

        return (unitsFromMemory + unitsFromProject).sortedByDescending { it.similarity }
    }
}
