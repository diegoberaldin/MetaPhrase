package com.github.diegoberaldin.metaphrase.domain.formats

import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository

internal class DefaultImportResourcesV2UseCase(
    private val baseUseCase: ImportResourcesUseCase,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
) : ImportResourcesV2UseCase {
    override suspend fun invoke(projectId: Int, paths: Map<String, String>, type: ResourceFileType) {
        for (lang in paths.keys) {
            val path = paths[lang].orEmpty()
            if (path.isNotEmpty()) {
                val segments = baseUseCase(
                    path = path,
                    type = type,
                )
                val languageId = languageRepository.getByCode(code = lang, projectId = projectId)?.id ?: 0
                if (languageId > 0) {
                    segmentRepository.createBatch(models = segments, languageId = languageId)
                }
            }
        }
    }
}
