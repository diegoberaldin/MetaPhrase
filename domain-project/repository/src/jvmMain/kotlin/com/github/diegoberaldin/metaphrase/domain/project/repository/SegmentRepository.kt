package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter

interface SegmentRepository {
    suspend fun create(model: SegmentModel, languageId: Int): Int

    suspend fun createBatch(models: List<SegmentModel>, languageId: Int)

    suspend fun update(model: SegmentModel): Int

    suspend fun delete(model: SegmentModel): Int

    suspend fun getAll(languageId: Int): List<SegmentModel>

    suspend fun getUntranslatable(languageId: Int): List<SegmentModel>

    suspend fun search(
        languageId: Int,
        baseLanguageId: Int = 0,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null,
        skip: Int = 0,
        limit: Int = 0,
    ): List<SegmentModel>

    suspend fun getById(id: Int): SegmentModel?

    suspend fun getByKey(key: String, languageId: Int): SegmentModel?
}

