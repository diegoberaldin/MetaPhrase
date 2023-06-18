package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import kotlinx.coroutines.flow.Flow

interface RecentProjectRepository {

    suspend fun observeAll(): Flow<List<RecentProjectModel>>
    suspend fun getAll(): List<RecentProjectModel>

    suspend fun create(model: RecentProjectModel): Int

    suspend fun delete(model: RecentProjectModel)
}
