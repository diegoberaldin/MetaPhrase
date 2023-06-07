package persistence.dao

import data.LanguageModel
import org.jetbrains.exposed.sql.ResultRow

interface LanguageDao {
    suspend fun create(model: LanguageModel, projectId: Int): Int

    suspend fun update(model: LanguageModel): Int

    suspend fun delete(model: LanguageModel): Int

    suspend fun getBase(projectId: Int): LanguageModel?

    suspend fun getAll(projectId: Int): List<LanguageModel>

    suspend fun getByCode(code: String, projectId: Int): LanguageModel?

    suspend fun getById(id: Int): LanguageModel?
    fun ResultRow.toModel(): LanguageModel
}
