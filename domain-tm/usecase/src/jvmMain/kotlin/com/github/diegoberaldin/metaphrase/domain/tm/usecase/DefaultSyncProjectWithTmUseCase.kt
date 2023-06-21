package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.tm.data.TranslationMemoryEntryModel
import com.github.diegoberaldin.metaphrase.domain.tm.repository.MemoryEntryRepository
import kotlinx.coroutines.withContext

internal class DefaultSyncProjectWithTmUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val segmentRepository: SegmentRepository,
    private val languageRepository: LanguageRepository,
    private val memoryEntryRepository: MemoryEntryRepository,
) : SyncProjectWithTmUseCase {
    override suspend fun invoke(projectId: Int) {
        withContext(dispatchers.io) {
            val project = projectRepository.getById(projectId) ?: return@withContext
            val origin = project.name
            memoryEntryRepository.deleteAll(origin)

            val baseLanguage = languageRepository.getBase(projectId) ?: return@withContext
            val otherLanguages = languageRepository.getAll(projectId).filter { it.code != baseLanguage.code }

            segmentRepository.getAll(baseLanguage.id).forEach { baseSegment ->
                val key = baseSegment.key
                val sourceText = baseSegment.text
                for (targetLang in otherLanguages) {
                    val localSegment = segmentRepository.getByKey(key = key, languageId = targetLang.id)
                    if (localSegment != null && localSegment.text.isNotEmpty()) {
                        val targetText = localSegment.text
                        val entryModel = TranslationMemoryEntryModel(
                            identifier = key,
                            origin = origin,
                            sourceText = sourceText,
                            sourceLang = baseLanguage.code,
                            targetText = targetText,
                            targetLang = targetLang.code,
                        )
                        val existing = memoryEntryRepository.getByIdentifier(
                            identifier = key,
                            origin = origin,
                            sourceLang = baseLanguage.code,
                            targetLang = targetLang.code,
                        )
                        if (existing == null) {
                            memoryEntryRepository.create(entryModel)
                        } else {
                            memoryEntryRepository.update(
                                existing.copy(sourceText = sourceText, targetText = targetText),
                            )
                        }
                    }
                }
            }
        }
    }
}
