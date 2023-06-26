package com.github.diegoberaldin.metaphrase.domain.glossary.usecase

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.FileReader

class DefaultImportGlossaryUseCase(
    private val repository: GlossaryTermRepository,
    private val dispatchers: CoroutineDispatcherProvider,
) : ImportGlossaryUseCase {
    override suspend fun invoke(path: String) {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return
        }
        withContext(dispatchers.io) {
            runCatching {
                FileReader(file).use { reader ->
                    val parseResult = CSVFormat.Builder.create(CSVFormat.DEFAULT).apply {
                        setIgnoreSurroundingSpaces(true)
                    }.build().parse(reader)
                    val records = parseResult.records.toList()
                    val headers = records.first().values().toList()
                    for (record in records.subList(1, records.size)) {
                        processRecord(record, headers)
                    }
                }
            }
        }
    }

    private suspend fun processRecord(
        record: CSVRecord,
        headers: List<String>,
    ) {
        val associationMap = mutableMapOf<String, List<Int>>()
        for (index in 0 until record.size()) {
            val value = record.get(index)
            val terms = value.split(",").map { it.trim() }
            val lang = headers.getOrNull(index).orEmpty()
            val termsIds = mutableListOf<Int>()
            for (term in terms) {
                if (term.isNotEmpty() && lang.isNotEmpty()) {
                    val existing = repository.get(lemma = term, lang = lang)
                    val id = existing?.id ?: run {
                        repository.create(
                            model = GlossaryTermModel(
                                lemma = term,
                                lang = lang,
                            ),
                        )
                    }
                    termsIds += id
                }
            }
            if (termsIds.isNotEmpty()) {
                associationMap[lang] = termsIds
            }
        }

        for (code in associationMap.keys) {
            val termIds = associationMap[code].orEmpty()
            val otherTermIds = associationMap.entries.filter { it.key != code }.flatMap { it.value }
            for (id in termIds) {
                for (otherId in otherTermIds) {
                    if (!repository.areAssociated(sourceId = id, targetId = otherId)) {
                        repository.associate(sourceId = id, targetId = otherId)
                    }
                }
            }
        }
    }
}
