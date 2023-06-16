package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.data.TranslationUnitTypeFilter
import org.jetbrains.exposed.sql.ResultRow

interface SegmentDao {
    suspend fun create(model: SegmentModel, languageId: Int): Int

    suspend fun createBatch(models: List<SegmentModel>, languageId: Int): List<ResultRow>

    suspend fun update(model: SegmentModel): Int

    suspend fun delete(model: SegmentModel): Int

    suspend fun getAll(languageId: Int): List<SegmentModel>

    suspend fun getUntranslatable(languageId: Int): List<SegmentModel>

    suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null,
        skip: Int = 0,
        limit: Int = 0,
    ): List<SegmentModel>

    suspend fun getById(id: Int): SegmentModel?

    suspend fun getByKey(key: String, languageId: Int): SegmentModel?
    fun ResultRow.toModel(): SegmentModel
}
