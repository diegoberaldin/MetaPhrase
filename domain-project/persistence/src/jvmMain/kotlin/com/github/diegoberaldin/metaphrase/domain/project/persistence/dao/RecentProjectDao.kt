package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel

/**
 * Contract for the recent project data access object.
 */
interface RecentProjectDao {
    /**
     * Get all recent projects.
     *
     * @return list of recent projects
     */
    suspend fun getAll(): List<RecentProjectModel>

    /**
     * Get a recent project by name.
     *
     * @param value name of the project
     * @return [RecentProjectModel] or null
     */
    suspend fun getByName(value: String): RecentProjectModel?

    /**
     * Delete a recent project.
     *
     * @param model recent project to delete
     */
    suspend fun delete(model: RecentProjectModel)

    /**
     * Create a recent project.
     *
     * @param model recent project to create
     * @return ID of the newly created recent project
     */
    suspend fun create(model: RecentProjectModel): Int
}
