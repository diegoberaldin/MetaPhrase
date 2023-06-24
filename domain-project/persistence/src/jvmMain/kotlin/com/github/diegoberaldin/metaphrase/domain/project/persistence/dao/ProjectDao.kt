package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel

/**
 * Contract for the project data access object.
 */
interface ProjectDao {
    /**
     * Create a new project.
     *
     * @param model project to create
     * @return ID of the newly created project
     */
    suspend fun create(model: ProjectModel): Int

    /**
     * Update a project.
     *
     * @param model project to update
     */
    suspend fun update(model: ProjectModel)

    /**
     * Delete a project.
     *
     * @param model project to delete
     */
    suspend fun delete(model: ProjectModel)

    /**
     * Delete all projects.
     */
    suspend fun deleteAll()

    /**
     * Get all projects in the DB.
     *
     * @return list of all projects
     */
    suspend fun getAll(): List<ProjectModel>

    /**
     * Get a project by ID.
     *
     * @param id project ID
     * @return [ProjectModel] or null
     */
    suspend fun getById(id: Int): ProjectModel?
}
