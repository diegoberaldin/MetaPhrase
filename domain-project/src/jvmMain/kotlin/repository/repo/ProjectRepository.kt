package repository.repo

import data.ProjectModel
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    suspend fun getAll(): List<ProjectModel>

    suspend fun observeAll(): Flow<List<ProjectModel>>

    suspend fun getById(id: Int): ProjectModel?
    fun observeById(id: Int): Flow<ProjectModel>

    suspend fun create(model: ProjectModel): Int

    suspend fun update(model: ProjectModel): Int

    suspend fun delete(model: ProjectModel): Int
}
