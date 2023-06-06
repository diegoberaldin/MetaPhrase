package translationmemory.datasource

import data.TranslationUnit
import language.repo.LanguageRepository
import localized
import repository.local.SegmentRepository
import translationmemory.similarity.SimilarityCalculator

internal class ProjectTranslationUnitSource(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val calculateSimilarity: SimilarityCalculator,
) : TranslationUnitSource {
    override suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit> {
        val baseLanguage = languageRepository.getBase(projectId) ?: return emptyList()
        val segments = segmentRepository.getAll(baseLanguage.id).filter { it.key != key }
        val original = segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: return emptyList()

        val res = mutableListOf<TranslationUnit>()
        for (s in segments) {
            val source = original.text
            val target = s.text
            val similarity = calculateSimilarity(segment1 = source, segment2 = target)
            if (similarity >= threshold) {
                val similarSource = segmentRepository.getByKey(key = s.key, languageId = baseLanguage.id)
                val similarTarget = segmentRepository.getByKey(key = s.key, languageId = languageId)
                if (similarSource != null && similarTarget != null && similarTarget.text.isNotEmpty()) {
                    res += TranslationUnit(
                        original = similarSource,
                        segment = similarTarget,
                        similarity = (similarity * 100).toInt(),
                        origin = "translation_unit_source_this_project".localized(),
                    )
                }
            }
        }
        return res
    }
}
