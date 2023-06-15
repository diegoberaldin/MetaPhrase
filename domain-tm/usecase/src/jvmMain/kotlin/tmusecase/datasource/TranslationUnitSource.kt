package tmusecase.datasource

import projectdata.TranslationUnit

internal interface TranslationUnitSource {
    suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit>
}
