package translationmemory.repo

import data.TranslationUnit

interface TranslationUnitSource {
    suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit>
}
