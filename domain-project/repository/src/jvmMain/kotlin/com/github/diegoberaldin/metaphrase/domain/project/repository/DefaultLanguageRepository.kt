package com.github.diegoberaldin.metaphrase.domain.project.repository

import com.github.diegoberaldin.metaphrase.domain.project.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.project.persistence.dao.LanguageDao
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import java.util.*

internal class DefaultLanguageRepository(
    private val dao: LanguageDao,
) : LanguageRepository {

    override fun getDefaultLanguages() = listOf(
        LanguageModel(code = Locale.ENGLISH.language),
        LanguageModel(code = Locale.FRENCH.language),
        LanguageModel(code = Locale.GERMAN.language),
        LanguageModel(code = Locale.ITALIAN.language),
        LanguageModel(code = "bg"),
        LanguageModel(code = "cs"),
        LanguageModel(code = "da"),
        LanguageModel(code = "el"),
        LanguageModel(code = "es"),
        LanguageModel(code = "et"),
        LanguageModel(code = "fi"),
        LanguageModel(code = "ga"),
        LanguageModel(code = "hr"),
        LanguageModel(code = "hu"),
        LanguageModel(code = "lt"),
        LanguageModel(code = "lv"),
        LanguageModel(code = "mt"),
        LanguageModel(code = "nl"),
        LanguageModel(code = "pl"),
        LanguageModel(code = "pt"),
        LanguageModel(code = "ro"),
        LanguageModel(code = "sk"),
        LanguageModel(code = "sl"),
        LanguageModel(code = "sw"),
    )

    override suspend fun getAll(projectId: Int) = dao.getAll(projectId)

    override suspend fun getBase(projectId: Int) = dao.getBase(projectId)

    override fun observeAll(projectId: Int) = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getAll(projectId)
            trySend(res)
        }
    }.distinctUntilChanged()

    override suspend fun getById(id: Int) = dao.getById(id)

    override suspend fun getByCode(code: String, projectId: Int) = dao.getByCode(code = code, projectId = projectId)

    override suspend fun delete(model: LanguageModel) = dao.delete(model = model)

    override suspend fun update(model: LanguageModel) = dao.update(model = model)

    override suspend fun create(model: LanguageModel, projectId: Int): Int =
        dao.create(model = model, projectId = projectId)
}
