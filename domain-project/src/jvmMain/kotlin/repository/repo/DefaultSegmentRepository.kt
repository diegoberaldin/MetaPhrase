package repository.repo

import data.SegmentModel
import data.TranslationUnitTypeFilter
import persistence.dao.SegmentDao

internal class DefaultSegmentRepository(
    private val dao: SegmentDao,
) : SegmentRepository {
    override suspend fun create(model: SegmentModel, languageId: Int) =
        dao.create(model = model, languageId = languageId)

    override suspend fun createBatch(models: List<SegmentModel>, languageId: Int) {
        dao.createBatch(models = models, languageId = languageId)
    }

    override suspend fun update(model: SegmentModel) = dao.update(model)

    override suspend fun delete(model: SegmentModel) = dao.delete(model)

    override suspend fun getAll(languageId: Int) = dao.getAll(languageId)

    override suspend fun getUntranslatable(languageId: Int) = dao.getUntranslatable(languageId)

    override suspend fun search(
        languageId: Int,
        filter: TranslationUnitTypeFilter,
        search: String?,
    ) = dao.search(
        languageId = languageId,
        filter = filter,
        search = search,
    )

    override suspend fun getById(id: Int) = dao.getById(id)

    override suspend fun getByKey(key: String, languageId: Int) = dao.getByKey(key = key, languageId = languageId)
}
