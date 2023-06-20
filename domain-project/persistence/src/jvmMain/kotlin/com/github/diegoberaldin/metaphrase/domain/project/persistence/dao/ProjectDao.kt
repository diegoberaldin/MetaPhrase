package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import org.jetbrains.exposed.sql.ResultRow

interface ProjectDao {
    suspend fun create(model: ProjectModel): Int

    suspend fun update(model: ProjectModel)

    suspend fun delete(model: ProjectModel)
    suspend fun deleteAll()

    suspend fun getAll(): List<ProjectModel>

    suspend fun getById(id: Int): ProjectModel?
    fun ResultRow.toModel(): ProjectModel
}
