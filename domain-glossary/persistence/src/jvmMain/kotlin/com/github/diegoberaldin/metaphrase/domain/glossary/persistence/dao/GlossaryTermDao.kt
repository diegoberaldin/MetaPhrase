package com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import org.jetbrains.exposed.sql.ResultRow

interface GlossaryTermDao {
    suspend fun insert(model: GlossaryTermModel): Int

    suspend fun getAll(): List<GlossaryTermModel>

    suspend fun getById(id: Int): GlossaryTermModel?

    suspend fun getBy(lemma: String, lang: String): GlossaryTermModel?

    suspend fun update(model: GlossaryTermModel): Int

    suspend fun delete(model: GlossaryTermModel): Int
    fun ResultRow.toModel(): GlossaryTermModel

    suspend fun areAssociated(sourceId: Int, targetId: Int): Boolean

    suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel>

    suspend fun getAllAssociated(model: GlossaryTermModel): List<GlossaryTermModel>

    suspend fun associate(sourceId: Int, targetId: Int)

    suspend fun disassociate(sourceId: Int, targetId: Int): Int

    suspend fun isStillReferenced(id: Int): Boolean
}
