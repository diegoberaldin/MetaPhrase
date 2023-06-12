package projectpersistence.dao

import data.ProjectModel
import org.jetbrains.exposed.sql.ResultRow

interface ProjectDao {
    suspend fun create(model: ProjectModel): Int

    suspend fun update(model: ProjectModel): Int

    suspend fun delete(model: ProjectModel): Int

    suspend fun getAll(): List<ProjectModel>

    suspend fun getById(id: Int): ProjectModel?
    fun ResultRow.toModel(): ProjectModel
}
