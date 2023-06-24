package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the project repository.
 */
interface ProjectRepository {
    /**
     * Set the flag indicating there are unsaved changes.
     *
     * @param value Value of the flag
     */
    fun setNeedsSaving(value: Boolean)

    /**
     * Observe the value of the flag indicating there are unsaved changes.
     *
     * @return a flow of distict values
     */
    fun observeNeedsSaving(): Flow<Boolean>

    /**
     * Scalar value of the flag indicating there are unsaved changes.
     *
     * @return a snapshot of the flag value
     */
    fun isNeedsSaving(): Boolean

    /**
     * Get all projects.
     *
     * @return list of projects
     */
    suspend fun getAll(): List<ProjectModel>

    /**
     * Get a project by id.
     *
     * @param id Project ID
     * @return [ProjectModel] or null if no such project exists
     */
    suspend fun getById(id: Int): ProjectModel?

    /**
     * Observe a project given its ID.
     *
     * @param id Project ID
     * @return [Flow] of the project from DB
     */
    fun observeById(id: Int): Flow<ProjectModel>

    /**
     * Create a project in the DB.
     *
     * @param model Project to create
     * @return ID of the newly created project
     */
    suspend fun create(model: ProjectModel): Int

    /**
     * Update an existing project.
     *
     * @param model Project to update
     */
    suspend fun update(model: ProjectModel)

    /**
     * Delete a project.
     *
     * @param model Project to delete
     */
    suspend fun delete(model: ProjectModel)

    /**
     * Delete all projects.
     */
    suspend fun deleteAll()
}
