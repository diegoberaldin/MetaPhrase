package com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource

import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

internal interface TranslationUnitSource {
    suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit>
}
