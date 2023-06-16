package com.github.diegoberaldin.metaphrase.domain.tm.usecase

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

interface GetSimilaritiesUseCase {
    suspend operator fun invoke(
        segment: SegmentModel,
        projectId: Int,
        languageId: Int,
        threshold: Float = 0.75f,
    ): List<TranslationUnit>
}
