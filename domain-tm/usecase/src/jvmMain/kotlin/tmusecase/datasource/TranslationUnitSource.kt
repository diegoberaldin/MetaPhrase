package tmusecase.datasource

import data.TranslationUnit

internal interface TranslationUnitSource {
    suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit>
}
