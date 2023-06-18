package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.ProjectDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive

internal class DefaultProjectRepository(
    private val dao: ProjectDao,
) : ProjectRepository {
    override suspend fun getAll(): List<ProjectModel> = dao.getAll()

    override suspend fun getById(id: Int): ProjectModel? = dao.getById(id)
    override fun observeById(id: Int): Flow<ProjectModel> = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getById(id)
            if (res != null) {
                trySend(res)
            }
            delay(500)
        }
    }.distinctUntilChanged()

    override suspend fun create(model: ProjectModel): Int = dao.create(model)

    override suspend fun update(model: ProjectModel) = dao.update(model)

    override suspend fun delete(model: ProjectModel) = dao.delete(model)
}
