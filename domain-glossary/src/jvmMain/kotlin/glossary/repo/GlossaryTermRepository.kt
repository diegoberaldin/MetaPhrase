package glossary.repo

import data.GlossaryTermModel
import persistence.dao.GlossaryTermDao

class GlossaryTermRepository(
    private val dao: GlossaryTermDao,
) {
    suspend fun create(model: GlossaryTermModel) = dao.insert(model)

    suspend fun getById(id: Int) = dao.getById(id)

    suspend fun get(lemma: String, lang: String) = dao.getBy(lemma = lemma, lang = lang)

    suspend fun update(model: GlossaryTermModel) = dao.update(model)

    suspend fun delete(model: GlossaryTermModel) = dao.delete(model)

    suspend fun areAssociated(sourceId: Int, targetId: Int) =
        dao.areAssociated(sourceId = sourceId, targetId = targetId)

    suspend fun associate(sourceId: Int, targetId: Int) = dao.associate(sourceId = sourceId, targetId = targetId)

    suspend fun disassociate(sourceId: Int, targetId: Int) = dao.disassociate(sourceId = sourceId, targetId = targetId)

    suspend fun isStillReferenced(id: Int) = dao.isStillReferenced(id)
}
