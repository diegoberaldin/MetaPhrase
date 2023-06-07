package persistence.dao

import data.GlossaryTermModel
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import persistence.entities.GlossaryTermEntity
import persistence.entities.GlossaryTermRelationshipEntity

internal class DefaultGlossaryTermDao : GlossaryTermDao {
    override suspend fun insert(model: GlossaryTermModel): Int = newSuspendedTransaction {
        GlossaryTermEntity.insertIgnore {
            it[lemma] = model.lemma
            it[lang] = model.lang
        }[GlossaryTermEntity.id].value
    }

    override suspend fun getById(id: Int): GlossaryTermModel? = newSuspendedTransaction {
        GlossaryTermEntity.select { GlossaryTermEntity.id eq id }.firstOrNull()?.toModel()
    }

    override suspend fun getBy(lemma: String, lang: String): GlossaryTermModel? = newSuspendedTransaction {
        GlossaryTermEntity.select {
            (GlossaryTermEntity.lemma eq lemma) and (GlossaryTermEntity.lang eq lang)
        }.firstOrNull()?.toModel()
    }

    override suspend fun update(model: GlossaryTermModel) = newSuspendedTransaction {
        GlossaryTermEntity.update(where = { GlossaryTermEntity.id eq model.id }) {
            it[lemma] = model.lemma
            it[lang] = model.lang
        }
    }

    override suspend fun delete(model: GlossaryTermModel) = newSuspendedTransaction {
        GlossaryTermEntity.deleteWhere { GlossaryTermEntity.id eq model.id }
    }

    override fun ResultRow.toModel(): GlossaryTermModel = GlossaryTermModel(
        id = this[GlossaryTermEntity.id].value,
        lemma = this[GlossaryTermEntity.lemma],
        lang = this[GlossaryTermEntity.lang],
    )

    override suspend fun areAssociated(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.select {
            ((GlossaryTermRelationshipEntity.id1 eq sourceId) and (GlossaryTermRelationshipEntity.id2 eq targetId))
                .or(((GlossaryTermRelationshipEntity.id2 eq sourceId) and (GlossaryTermRelationshipEntity.id1 eq targetId)))
        }.count() > 0
    }

    override suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel> =
        newSuspendedTransaction {
            val terms1 = GlossaryTermEntity.alias("terms1")
            val terms2 = GlossaryTermEntity.alias("terms2")
            val termsWithId1 = terms1.join(
                otherTable = GlossaryTermRelationshipEntity,
                onColumn = terms1[GlossaryTermEntity.id],
                otherColumn = GlossaryTermRelationshipEntity.id1,
                joinType = JoinType.INNER,
            ).join(
                otherTable = terms2,
                onColumn = GlossaryTermRelationshipEntity.id2,
                otherColumn = terms2[GlossaryTermEntity.id],
                joinType = JoinType.INNER,
            )
                .select { (terms1[GlossaryTermEntity.id] eq model.id) and (terms2[GlossaryTermEntity.lang] eq otherLang) }
                .map {
                    GlossaryTermModel(
                        id = it[terms2[GlossaryTermEntity.id]].value,
                        lemma = it[terms2[GlossaryTermEntity.lemma]],
                        lang = it[terms2[GlossaryTermEntity.lang]],
                    )
                }
            val termsWithId2 = terms1.join(
                otherTable = GlossaryTermRelationshipEntity,
                onColumn = terms1[GlossaryTermEntity.id],
                otherColumn = GlossaryTermRelationshipEntity.id2,
                joinType = JoinType.INNER,
            ).join(
                otherTable = terms2,
                onColumn = GlossaryTermRelationshipEntity.id1,
                otherColumn = terms2[GlossaryTermEntity.id],
                joinType = JoinType.INNER,
            )
                .select { (terms1[GlossaryTermEntity.id] eq model.id) and (terms2[GlossaryTermEntity.lang] eq otherLang) }
                .map {
                    GlossaryTermModel(
                        id = it[terms2[GlossaryTermEntity.id]].value,
                        lemma = it[terms2[GlossaryTermEntity.lemma]],
                        lang = it[terms2[GlossaryTermEntity.lang]],
                    )
                }

            (termsWithId1.toSet() + termsWithId2).toList()
        }

    override suspend fun associate(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        if (areAssociated(sourceId, targetId)) return@newSuspendedTransaction
        GlossaryTermRelationshipEntity.insertIgnore {
            it[id1] = sourceId
            it[id2] = targetId
        }
    }

    override suspend fun disassociate(sourceId: Int, targetId: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.deleteWhere {
            ((id1 eq sourceId) and (id2 eq targetId)).or(((id2 eq sourceId) and (id1 eq targetId)))
        }
    }

    override suspend fun isStillReferenced(id: Int) = newSuspendedTransaction {
        GlossaryTermRelationshipEntity.select {
            (GlossaryTermRelationshipEntity.id1 eq id) or (GlossaryTermRelationshipEntity.id2 eq id)
        }.count() > 0
    }
}