package com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource

import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.similarity.SimilarityCalculator

/**
 * Data source that retrieves the matches from the TM.
 *
 * @property languageRepository
 * @property segmentRepository
 * @property memoryEntryRepository
 * @property calculateSimilarity
 * @constructor Create [MemoryTranslationUnitSource]
 */
internal class MemoryTranslationUnitSource(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val memoryEntryRepository: MemoryEntryRepository,
    private val calculateSimilarity: SimilarityCalculator,
) : TranslationUnitSource {
    override suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit> {
        val baseLanguage = languageRepository.getBase(projectId) ?: return emptyList()
        val currentLanguage = languageRepository.getById(languageId) ?: return emptyList()
        val entries = memoryEntryRepository.getEntries(sourceLang = baseLanguage.code, targetLang = currentLanguage.code)
        val original = segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: return emptyList()
        val res = mutableListOf<TranslationUnit>()
        for (e in entries) {
            val source = original.text
            val target = e.sourceText
            val similarity = calculateSimilarity(segment1 = source, segment2 = target)
            if (similarity >= threshold) {
                if (e.sourceText.isNotEmpty() && e.targetText.isNotEmpty()) {
                    res += TranslationUnit(
                        original = SegmentModel(text = e.sourceText),
                        segment = SegmentModel(text = e.targetText),
                        similarity = (similarity * 100).toInt(),
                        origin = e.origin,
                    )
                }
            }
        }

        return res
    }
}
