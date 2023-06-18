package com.github.diegoberaldin.metaphrase.domain.project.persistence.dao

import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel

interface RecentProjectDao {
    suspend fun getAll(): List<RecentProjectModel>
    suspend fun getByName(value: String): RecentProjectModel?
    suspend fun delete(model: RecentProjectModel)
    suspend fun create(model: RecentProjectModel): Int
}
