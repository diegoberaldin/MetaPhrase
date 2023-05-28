package translationmemory.repo

import data.LanguageModel
import data.SegmentModel
import data.TranslationUnit
import repository.local.LanguageRepository
import repository.local.SegmentRepository
import kotlin.math.max

internal class DefaultTranslationMemoryRepository(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : TranslationMemoryRepository {
    override suspend fun getSimilarities(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float,
    ): List<TranslationUnit> {
        val baseLanguage = languageRepository.getBase(projectId) ?: return emptyList()
        val original = segmentRepository.getByKey(key = segment.key, languageId = baseLanguage.id) ?: return emptyList()

        val res = mutableListOf<TranslationUnit>()
        val segments = getReferenceSegments(baseLanguage, segment)
        for (segment in segments) {
            val source = original.text
            val target = segment.text
            val distance = levenshteinDistance(source = source, target = target)
            val similarity = distance.toFloat() / max(source.length, target.length)
            if (similarity >= threshold) {
                val similarSource = segmentRepository.getByKey(key = segment.key, languageId = baseLanguage.id)
                val similarTarget = segmentRepository.getByKey(key = segment.key, languageId = languageId)
                if (similarSource != null && similarTarget != null) {
                    res += TranslationUnit(
                        original = similarSource,
                        segment = similarTarget,
                        similarity = (similarity * 100).toInt(),
                    )
                }
            }
        }
        return res
    }

    private suspend fun getReferenceSegments(
        baseLanguage: LanguageModel,
        segment: SegmentModel,
    ) = segmentRepository.getAll(baseLanguage.id).filter { it.key != segment.key }
}
