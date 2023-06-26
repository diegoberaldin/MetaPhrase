package com.github.diegoberaldin.metaphrase.domain.glossary.repository

import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel

/**
 * Contract for the glossary term repository.
 */
interface GlossaryTermRepository {
    /**
     * Create a new glossary term.
     *
     * @param model Term to insert
     * @return ID of the newly created term
     */
    suspend fun create(model: GlossaryTermModel): Int

    /**
     * Get a term by ID.
     *
     * @param id Term ID
     * @return [GlossaryTermModel] or null if no such term exists
     */
    suspend fun getById(id: Int): GlossaryTermModel?

    /**
     * Get a term by its lemma and language
     *
     * @param lemma Term lemma
     * @param lang Language code
     * @return [GlossaryTermModel] or null if no such term exists
     */
    suspend fun get(lemma: String, lang: String): GlossaryTermModel?

    /**
     * Get all glossary terms.
     *
     * @return list of terms
     */
    suspend fun getAll(): List<GlossaryTermModel>

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
     * Delete all glossary terms.
     */
    suspend fun deleteAll()

    /**
     * Determine whether two glossary terms are associated.
     *
     * @param sourceId Source term ID
     * @param targetId Target term ID
     * @return true if the terms are associated, false otherwise
     */
    suspend fun areAssociated(sourceId: Int, targetId: Int): Boolean

    /**
     * Get all the terms associated with the given one.
     *
     * @param model Source term
     * @param otherLang Target language code
     * @return list of associated terms in the target language (if any)
     */
    suspend fun getAssociated(model: GlossaryTermModel, otherLang: String): List<GlossaryTermModel>

    /**
     * Get all the terms associated with the given one, regardless of the language.
     *
     * @param model Source term
     * @return list of all associated terms
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
     */
    suspend fun disassociate(sourceId: Int, targetId: Int)

    /**
     * Determine whether a term still referenced by at least another term in the glossary.
     *
     * @param id Term ID
     * @return true if there is at least another term associated with it, false otherwise
     */
    suspend fun isStillReferenced(id: Int): Boolean
}
