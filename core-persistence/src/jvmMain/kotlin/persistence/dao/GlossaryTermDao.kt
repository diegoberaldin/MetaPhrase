package persistence.dao

import data.GlossaryTermModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import persistence.entities.GlossaryTermEntity
import persistence.entities.GlossaryTermRelationshipEntity

class GlossaryTermDao {
    suspend fun insert(model: GlossaryTermModel): Int = newSuspendedTransaction {
        GlossaryTermEntity.insertIgnore {
            it[lemma] = model.lemma
            it[lang] = model.lang
        }[GlossaryTermEntity.id].value
    }

    suspend fun getById(id: Int): GlossaryTermModel? = newSuspendedTransaction {
        GlossaryTermEntity.select { GlossaryTermEntity.id eq id }.firstOrNull()?.toModel()
    }

    suspend fun getBy(lemma: String, lang: String): GlossaryTermModel? = newSuspendedTransaction {
        GlossaryTermEntity.select {
            (GlossaryTermEntity.lemma eq lemma) and (GlossaryTermEntity.lang eq lang)
        }.firstOrNull()?.toModel()
    }

    suspend fun update(model: GlossaryTermModel) = newSuspendedTransaction {
        GlossaryTermEntity.update(where = { GlossaryTermEntity.id eq model.id }) {
            it[lemma] = model.lemma
            it[lang] = model.lang
        }
    }

    suspend fun delete(model: GlossaryTermModel) = newSuspendedTransaction {
        GlossaryTermEntity.deleteWhere { GlossaryTermEntity.id eq model.id }
    }

    private fun ResultRow.toModel(): GlossaryTermModel = GlossaryTermModel(
        id = this[GlossaryTermEntity.id].value,
        lemma = this[GlossaryTermEntity.lemma],
        lang = this[GlossaryTermEntity.lang],
    )

    suspend fun areAssociated(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.select {
            ((GlossaryTermRelationshipEntity.id1 eq sourceId) and (GlossaryTermRelationshipEntity.id2 eq targetId))
                .or(((GlossaryTermRelationshipEntity.id2 eq sourceId) and (GlossaryTermRelationshipEntity.id1 eq targetId)))
        }.count() > 0
    }

    suspend fun associate(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        if (areAssociated(sourceId, targetId)) return@newSuspendedTransaction
        GlossaryTermRelationshipEntity.insertIgnore {
            it[id1] = sourceId
            it[id2] = targetId
        }
    }

    suspend fun disassociate(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.deleteWhere {
            ((id1 eq sourceId) and (id2 eq targetId)).or(((id2 eq sourceId) and (id1 eq targetId)))
        }
    }

    suspend fun isStillReferenced(id: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.select {
            (GlossaryTermRelationshipEntity.id1 eq id) or (GlossaryTermRelationshipEntity.id2 eq id)
        }.count() > 0
    }
}
