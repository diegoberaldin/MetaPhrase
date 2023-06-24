package com.github.diegoberaldin.metaphrase.domain.language.repository

import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the language repository.
 */
interface LanguageRepository {
    /**
     * Get the default languages for project creation.
     *
     * @return list of supported languages
     */
    fun getDefaultLanguages(): List<LanguageModel>

    /**
     * Get all the languages within a given project.
     *
     * @param projectId Project ID
     * @return list of languages added to the project
     */
    suspend fun getAll(projectId: Int): List<LanguageModel>

    /**
     * Get the source (base) language of a project.
     *
     * @param projectId Project ID
     * @return [LanguageModel] or null of no source language exists
     */
    suspend fun getBase(projectId: Int): LanguageModel?

    /**
     * Observe all the languages within a project.
     *
     * @param projectId Project ID
     * @return flow of distinct language lists
     */
    fun observeAll(projectId: Int): Flow<List<LanguageModel>>

    /**
     * Get a language by its id.
     *
     * @param id Language ID
     * @return [LanguageModel] or null if no such language exists
     */
    suspend fun getById(id: Int): LanguageModel?

    /**
     * Get a language by its code.
     *
     * @param code Language code (two letters ISO 693-1 code)
     * @param projectId Project ID
     * @return [LanguageModel] or null if no such language exists
     */
    suspend fun getByCode(code: String, projectId: Int): LanguageModel?

    /**
     * Delete a language.
     *
     * @param model Language to delete
     */
    suspend fun delete(model: LanguageModel)

    /**
     * Update a language.
     *
     * @param model Language to update
     */
    suspend fun update(model: LanguageModel)

    /**
     * Create a new language within a given project.
     *
     * @param model Language to create
     * @param projectId Project ID
     * @return ID of the newly created language
     */
    suspend fun create(model: LanguageModel, projectId: Int): Int
}
