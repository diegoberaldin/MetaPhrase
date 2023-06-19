package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun setNeedsSaving(value: Boolean)
    fun observeNeedsSaving(): Flow<Boolean>
    fun isNeedsSaving(): Boolean
    suspend fun getAll(): List<ProjectModel>
    suspend fun getById(id: Int): ProjectModel?
    fun observeById(id: Int): Flow<ProjectModel>
    suspend fun create(model: ProjectModel): Int
    suspend fun update(model: ProjectModel)
    suspend fun delete(model: ProjectModel)
    suspend fun deleteAll()
}
