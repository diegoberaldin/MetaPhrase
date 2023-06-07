package glossary.repo

import data.GlossaryTermModel
import persistence.dao.GlossaryTermDao

internal class DefaultGlossaryTermRepository(
    private val dao: GlossaryTermDao,
) : GlossaryTermRepository {
    override suspend fun create(model: GlossaryTermModel) = dao.insert(model)

    override suspend fun getById(id: Int) = dao.getById(id)

    override suspend fun get(lemma: String, lang: String) = dao.getBy(lemma = lemma, lang = lang)

    override suspend fun update(model: GlossaryTermModel) = dao.update(model)

    override suspend fun delete(model: GlossaryTermModel) = dao.delete(model)

    override suspend fun areAssociated(sourceId: Int, targetId: Int) =
        dao.areAssociated(sourceId = sourceId, targetId = targetId)

    override suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel> =
        dao.getAssociated(model = model, otherLang = otherLang)

    override suspend fun associate(sourceId: Int, targetId: Int) =
        dao.associate(sourceId = sourceId, targetId = targetId)

    override suspend fun disassociate(sourceId: Int, targetId: Int) =
        dao.disassociate(sourceId = sourceId, targetId = targetId)

    override suspend fun isStillReferenced(id: Int) = dao.isStillReferenced(id)
}
