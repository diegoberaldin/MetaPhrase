package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import kotlinx.coroutines.flow.Flow

/**
 * Contract for the recent project repository.
 */
interface RecentProjectRepository {
    /**
     * Observe all recent projects
     *
     * @return flow of the list of recent projects
     */
    suspend fun observeAll(): Flow<List<RecentProjectModel>>

    /**
     * Get all recent projects.
     *
     * @return list of recent projects
     */
    suspend fun getAll(): List<RecentProjectModel>

    /**
     * Get a project by name.
     *
     * @param value Project name
     * @return [RecentProjectModel] or null if no such project exists
     */
    suspend fun getByName(value: String): RecentProjectModel?

    /**
     * Create a new project.
     *
     * @param model Project to create
     * @return ID of the newly created project
     */
    suspend fun create(model: RecentProjectModel): Int

    /**
     * Delete a project.
     *
     * @param model Project to delete
     */
    suspend fun delete(model: RecentProjectModel)
}
