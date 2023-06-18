package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.RecentProjectDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive

internal class DefaultRecentProjectRepository(
    private val dao: RecentProjectDao,
) : RecentProjectRepository {

    override suspend fun observeAll(): Flow<List<RecentProjectModel>> = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getAll()
            trySend(res)
            delay(500)
        }
    }.distinctUntilChanged()

    override suspend fun getAll(): List<RecentProjectModel> = dao.getAll()

    override suspend fun create(model: RecentProjectModel) = dao.create(model)

    override suspend fun delete(model: RecentProjectModel) = dao.delete(model)
}
