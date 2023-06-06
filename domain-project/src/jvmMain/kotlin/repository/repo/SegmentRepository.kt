package repository.repo

import data.SegmentModel
import data.TranslationUnitTypeFilter

interface SegmentRepository {
    suspend fun create(model: SegmentModel, languageId: Int): Int

    suspend fun createBatch(models: List<SegmentModel>, languageId: Int)

    suspend fun update(model: SegmentModel): Int

    suspend fun delete(model: SegmentModel): Int

    suspend fun getAll(languageId: Int): List<SegmentModel>

    suspend fun getUntranslatable(languageId: Int): List<SegmentModel>

    suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null,
    ): List<SegmentModel>

    suspend fun getById(id: Int): SegmentModel?

    suspend fun getByKey(key: String, languageId: Int): SegmentModel?
}

