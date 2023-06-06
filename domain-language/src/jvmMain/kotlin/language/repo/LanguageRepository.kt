package language.repo

import data.LanguageModel
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import persistence.dao.LanguageDao
import java.util.*

class LanguageRepository(
    private val dao: LanguageDao,
) {

    fun getDefaultLanguages() = listOf(
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

    suspend fun getAll(projectId: Int) = dao.getAll(projectId)

    suspend fun getBase(projectId: Int) = dao.getBase(projectId)

    fun observeAll(projectId: Int) = channelFlow {
        while (true) {
            if (!isActive) {
                break
            }
            val res = getAll(projectId)
            trySend(res)
        }
    }.distinctUntilChanged()

    suspend fun getById(id: Int) = dao.getById(id)

    suspend fun getByCode(code: String, projectId: Int) = dao.getByCode(code = code, projectId = projectId)

    suspend fun delete(model: LanguageModel) = dao.delete(model = model)

    suspend fun update(model: LanguageModel) = dao.update(model = model)

    suspend fun create(model: LanguageModel, projectId: Int): Int = dao.create(model = model, projectId = projectId)
}
