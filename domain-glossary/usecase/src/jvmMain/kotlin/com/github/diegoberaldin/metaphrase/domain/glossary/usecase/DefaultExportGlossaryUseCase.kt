package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import androidx.compose.runtime.key
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

class DefaultExportGlossaryUseCase(
    private val repository: GlossaryTermRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportGlossaryUseCase {
    override suspend fun invoke(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        if (!file.canWrite()) {
            return
        }

        withContext(dispatchers.io) {
            FileWriter(file).use { writer ->
                val content = getContent()
                writer.write(content)
            }
        }
    }

    private suspend fun getContent(): String {
        val conceptSets = mutableListOf<MutableSet<Int>>()
        val terms = repository.getAll()
        for (term in terms) {
            val set = conceptSets.firstOrNull { it.contains(term.id) } ?: run {
                val res = mutableSetOf<Int>()
                conceptSets += res
                res
            }
            set += term.id
            val associated = repository.getAllAssociated(term)
            for (aTerm in associated) {
                set += aTerm.id
            }
        }
        val listOfMaps = conceptSets.map { set ->
            set.mapNotNull { termId -> repository.getById(termId) }
                .groupBy { term -> term.lang }
                .map { entry -> entry.key to entry.value.joinToString(", ") { term -> term.lemma } }
                .toMap()
        }

        val keys = listOfMaps.fold(emptySet<String>()) { acc, e ->
            acc + e.keys
        }
        return buildString {
            // header
            append(keys.joinToString(", ")).append("\n")
            for (m in listOfMaps) {
                val row = keys.map { m[it] }.joinToString(", ") { "\"$it\"" }
                append(row).append("\n")
            }
        }
    }
}
