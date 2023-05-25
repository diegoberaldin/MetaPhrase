package repository.usecase

import data.LanguageModel
import data.SegmentModel
import repository.local.LanguageRepository
import repository.local.SegmentRepository

class ImportSegmentsUseCase(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) {
    suspend operator fun invoke(
        segments: List<SegmentModel>,
        language: LanguageModel,
        projectId: Int,
    ) {
        val otherLanguages = languageRepository.getAll(projectId).filter { it.code != language.code }

        for (segment in segments) {
            val key = segment.key
            val existing = segmentRepository.getByKey(key = key, languageId = language.id)
            if (existing != null) {
                val toUpdate = existing.copy(text = segment.text)
                segmentRepository.update(toUpdate)
            } else {
                segmentRepository.create(model = segment, languageId = language.id)
            }

            // make sure a segment with the same key is present (albeit empty) for all languages if translatable
            for (lang in otherLanguages) {
                val otherSegment = segmentRepository.getByKey(key = key, languageId = lang.id)
                if (otherSegment == null && segment.translatable) {
                    val toInsert = SegmentModel(key = key)
                    segmentRepository.create(model = toInsert, languageId = lang.id)
                }
            }
        }
    }
}
