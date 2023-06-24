package com.github.diegoberaldin.metaphrase.domain.tm.usecase.datasource

import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnit

/**
 * Contract for all translation memory data sources used to get entries for a given message.
 */
internal interface TranslationUnitSource {
    /**
     * Get units from the trasnlation memory.
     *
     * @param projectId project ID
     * @param key message key
     * @param threshold similarity threshold
     * @param languageId language ID
     * @return
     */
    suspend fun getUnits(
        projectId: Int,
        key: String,
        threshold: Float,
        languageId: Int,
    ): List<TranslationUnit>
}
