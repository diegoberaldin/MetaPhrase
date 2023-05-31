package translationmemory.repo

import data.SegmentModel
import data.TranslationUnit
import repository.local.LanguageRepository
import repository.local.MemoryEntryRepository
import repository.local.SegmentRepository

internal class MemoryTranslationUnitSource(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val memoryEntryRepository: MemoryEntryRepository,
    private val calculateSimilarity: SimilarityCalculator,
) : TranslationUnitSource {
    override suspend fun getUnits(projectId: Int, key: String, threshold: Float, languageId: Int): List<TranslationUnit> {
        val baseLanguage = languageRepository.getBase(projectId) ?: return emptyList()
        val currentLanguage = languageRepository.getById(languageId) ?: return emptyList()
        val entries = memoryEntryRepository.getAll(sourceLang = baseLanguage.code, targetLang = currentLanguage.code)
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
                    )
                }
            }
        }

        return res
    }
}
