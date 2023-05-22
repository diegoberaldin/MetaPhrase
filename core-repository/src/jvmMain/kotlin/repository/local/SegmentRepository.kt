package repository.local

import data.SegmentModel
import persistence.dao.SegmentDao

class SegmentRepository(
    private val dao: SegmentDao,
) {
    suspend fun create(model: SegmentModel, languageId: Int) = dao.create(model = model, languageId = languageId)

    suspend fun update(model: SegmentModel) = dao.update(model)

    suspend fun delete(model: SegmentModel) = dao.delete(model)

    suspend fun getAll(languageId: Int) = dao.getAll(languageId)

    suspend fun getAllTranslatable(languageId: Int) = dao.getAllTranslatable(languageId)

    suspend fun getAllUntranslated(languageId: Int) = dao.getAllUntranslated(languageId)

    suspend fun getById(id: Int) = dao.getById(id)

    suspend fun getByKey(key: String, languageId: Int) = dao.getByKey(key = key, languageId = languageId)
}
