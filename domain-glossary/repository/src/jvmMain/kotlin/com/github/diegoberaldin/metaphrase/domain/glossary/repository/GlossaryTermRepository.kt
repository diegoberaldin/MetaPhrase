package com.github.diegoberaldin.metaphrase.domain.glossary.repository

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

interface GlossaryTermRepository {
    suspend fun create(model: GlossaryTermModel): Int

    suspend fun getById(id: Int): GlossaryTermModel?

    suspend fun get(lemma: String, lang: String): GlossaryTermModel?
    suspend fun getAll(): List<GlossaryTermModel>

    suspend fun update(model: GlossaryTermModel): Int

    suspend fun delete(model: GlossaryTermModel): Int
    suspend fun deleteAll()

    suspend fun areAssociated(sourceId: Int, targetId: Int): Boolean

    suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel>

    suspend fun getAllAssociated(model: GlossaryTermModel): List<GlossaryTermModel>

    suspend fun associate(sourceId: Int, targetId: Int)

    suspend fun disassociate(sourceId: Int, targetId: Int): Int

    suspend fun isStillReferenced(id: Int): Boolean
}