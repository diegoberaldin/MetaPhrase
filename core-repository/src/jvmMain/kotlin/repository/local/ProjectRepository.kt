package repository.local

import data.ProjectModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import persistence.dao.ProjectDao

class ProjectRepository(
    private val dao: ProjectDao,
) {
    suspend fun getAll(): List<ProjectModel> = dao.getAll()
    suspend fun observeAll(): Flow<List<ProjectModel>> = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getAll()
            trySend(res)
            delay(1_000)
        }
    }

    suspend fun getById(id: Int): ProjectModel? = dao.getById(id)
    fun observeById(id: Int): Flow<ProjectModel> = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getById(id)
            if (res != null) {
                trySend(res)
            }
            delay(1_000)
        }
    }

    suspend fun create(model: ProjectModel): Int = dao.create(model)

    suspend fun update(model: ProjectModel) = dao.update(model)

    suspend fun delete(model: ProjectModel) = dao.delete(model)
}
