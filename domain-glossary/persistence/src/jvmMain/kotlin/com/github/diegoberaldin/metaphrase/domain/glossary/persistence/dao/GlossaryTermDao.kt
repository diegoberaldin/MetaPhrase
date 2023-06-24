package com.github.diegoberaldin.metaphrase.domain.glossary.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import org.jetbrains.exposed.sql.ResultRow

/**
 * Contract for the glossary term dao.
 */
interface GlossaryTermDao {
    /**
     * Create a new glossary term.
     *
     * @param model Term to insert
     * @return ID of the newly created term
     */
    suspend fun insert(model: GlossaryTermModel): Int

    /**
     * Get all the terms in the glossary.
     *
     * @return list of terms
     */
    suspend fun getAll(): List<GlossaryTermModel>

    /**
     * Get a tern given its id.
     *
     * @param id Term ID
     * @return [GlossaryTermModel] or null
     */
    suspend fun getById(id: Int): GlossaryTermModel?

    /**
     * Get a term given its lemma and language.
     *
     * @param lemma Term lemma
     * @param lang Language code (ISO 639-1)
     * @return [GlossaryTermModel] or null if no such term exists
     */
    suspend fun getBy(lemma: String, lang: String): GlossaryTermModel?

    /**
     * Update a glossary term.
     *
     * @param model Term to update
     */
    suspend fun update(model: GlossaryTermModel)

    /**
     * Delete a glossary term.
     *
     * @param model Term to delete
     */
    suspend fun delete(model: GlossaryTermModel)

    /**
     * Delete all the terms in the glossary.
     */
    suspend fun deleteAll()
    fun ResultRow.toModel(): GlossaryTermModel

    /**
     * Determine whether two terms are associated.
     *
     * @param sourceId Source term ID
     * @param targetId Target term ID
     * @return true if the terms are associated, false otherwise
     */
    suspend fun areAssociated(sourceId: Int, targetId: Int): Boolean

    /**
     * Get the terms associated with a given term.
     *
     * @param model Term (source)
     * @param otherLang Target language code
     * @return list of associated terms
     */
    suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel>

    /**
     * Get all terms associated with a given term no matter the language.
     *
     * @param model Term (source)
     * @return list of associated terms
     */
    suspend fun getAllAssociated(model: GlossaryTermModel): List<GlossaryTermModel>

    /**
     * Create an association between two terms.
     *
     * @param sourceId Source term ID
     * @param targetId Target term ID
     */
    suspend fun associate(sourceId: Int, targetId: Int)

    /**
     * Remove the association between two terms.
     *
     * @param sourceId Source term ID
     * @param targetId Target term ID
     * @return
     */
    suspend fun disassociate(sourceId: Int, targetId: Int): Int

    /**
     * Determine whether a term is still referenced by any other term in the glossary.
     *
     * @param id Term ID
     * @return true if there is still at least one other term referencing this one
     */
    suspend fun isStillReferenced(id: Int): Boolean
}
