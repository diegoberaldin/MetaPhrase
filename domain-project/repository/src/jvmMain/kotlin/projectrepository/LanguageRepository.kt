package projectrepository

import data.LanguageModel
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getDefaultLanguages(): List<LanguageModel>

    suspend fun getAll(projectId: Int): List<LanguageModel>

    suspend fun getBase(projectId: Int): LanguageModel?
    fun observeAll(projectId: Int): Flow<List<LanguageModel>>

    suspend fun getById(id: Int): LanguageModel?

    suspend fun getByCode(code: String, projectId: Int): LanguageModel?

    suspend fun delete(model: LanguageModel): Int

    suspend fun update(model: LanguageModel): Int

    suspend fun create(model: LanguageModel, projectId: Int): Int
}
