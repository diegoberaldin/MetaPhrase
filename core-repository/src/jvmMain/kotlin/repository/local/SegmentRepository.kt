package repository.local

import data.SegmentModel
import data.TranslationUnitTypeFilter
import persistence.dao.SegmentDao

class SegmentRepository(
    private val dao: SegmentDao,
) {
    suspend fun create(model: SegmentModel, languageId: Int) = dao.create(model = model, languageId = languageId)
    suspend fun createBatch(models: List<SegmentModel>, languageId: Int) =
        dao.createBatch(models = models, languageId = languageId)

    suspend fun update(model: SegmentModel) = dao.update(model)

    suspend fun delete(model: SegmentModel) = dao.delete(model)

    suspend fun getAll(languageId: Int) = dao.getAll(languageId)

    suspend fun getUntranslatable(languageId: Int) = dao.getUntranslatable(languageId)

    suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter = TranslationUnitTypeFilter.ALL,
        search: String? = null,
    ) = dao.search(
        languageId = languageId,
        filter = filter,
        search = search,
    )

    suspend fun getById(id: Int) = dao.getById(id)

    suspend fun getByKey(key: String, languageId: Int) = dao.getByKey(key = key, languageId = languageId)
}
