package com.github.diegoberaldin.metaphrase.domain.language.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel

/**
 * Contract for the language dao.
 */
interface LanguageDao {
    /**
     * Create a language within a given project.
     *
     * @param model Language to create
     * @param projectId Project ID
     * @return ID of the newly created language
     */
    suspend fun create(model: LanguageModel, projectId: Int): Int

    /**
     * Update a language.
     *
     * @param model Language to update
     */
    suspend fun update(model: LanguageModel)

    /**
     * Delete a language.
     *
     * @param model Language to delete
     */
    suspend fun delete(model: LanguageModel)

    /**
     * Get the source language for a given project.
     *
     * @param projectId Project ID
     * @return [LanguageModel] or null if no source language exists
     */
    suspend fun getBase(projectId: Int): LanguageModel?

    /**
     * Get all the languages for a project.
     *
     * @param projectId Project ID
     * @return list of languages
     */
    suspend fun getAll(projectId: Int): List<LanguageModel>

    /**
     * Get a language by code within a given project.
     *
     * @param code Language code (two letters ISO 693-1 code)
     * @param projectId Project ID
     * @return [LanguageModel] or null if no such language exists
     */
    suspend fun getByCode(code: String, projectId: Int): LanguageModel?

    /**
     * Get a language given its ID.
     *
     * @param id Language ID
     * @return [LanguageModel] or null
     */
    suspend fun getById(id: Int): LanguageModel?
}
