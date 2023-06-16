package com.github.diegoberaldin.metaphrase.domain.project.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import kotlinx.coroutines.withContext

internal class DefaultImportSegmentsUseCase(
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ImportSegmentsUseCase {
    override suspend operator fun invoke(
        segments: List<SegmentModel>,
        language: LanguageModel,
        projectId: Int,
    ) {
        withContext(dispatchers.io) {
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
}
