package com.github.diegoberaldin.metaphrase.domain.glossary.repository

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao.GlossaryTermDao

internal class DefaultGlossaryTermRepository(
    private val dao: GlossaryTermDao,
) : GlossaryTermRepository {
    override suspend fun create(model: GlossaryTermModel) = dao.create(model)

    override suspend fun getById(id: Int) = dao.getById(id)

    override suspend fun get(lemma: String, lang: String) = dao.getBy(lemma = lemma, lang = lang)

    override suspend fun getAll() = dao.getAll()

    override suspend fun update(model: GlossaryTermModel) = dao.update(model)

    override suspend fun delete(model: GlossaryTermModel) = dao.delete(model)

    override suspend fun deleteAll() = dao.deleteAll()

    override suspend fun areAssociated(sourceId: Int, targetId: Int) =
        dao.areAssociated(sourceId = sourceId, targetId = targetId)

    override suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel> =
        dao.getAssociated(model = model, otherLang = otherLang)

    override suspend fun getAllAssociated(model: GlossaryTermModel) = dao.getAllAssociated(model)

    override suspend fun associate(sourceId: Int, targetId: Int) =
        dao.associate(sourceId = sourceId, targetId = targetId)

    override suspend fun disassociate(sourceId: Int, targetId: Int) =
        dao.disassociate(sourceId = sourceId, targetId = targetId)

    override suspend fun isStillReferenced(id: Int) = dao.isStillReferenced(id)
}
