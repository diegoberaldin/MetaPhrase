package tmusecase

import common.coroutines.CoroutineDispatcherProvider
import data.TranslationMemoryEntryModel
import kotlinx.coroutines.withContext
import projectrepository.LanguageRepository
import projectrepository.ProjectRepository
import projectrepository.SegmentRepository
import tmrepository.MemoryEntryRepository

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
                            origin = origin,
                            sourceText = sourceText,
                            sourceLang = baseLanguage.code,
                            targetText = targetText,
                            targetLang = targetLang.code,
                        )
                        memoryEntryRepository.create(entryModel)
                    }
                }
            }
        }
    }
}
