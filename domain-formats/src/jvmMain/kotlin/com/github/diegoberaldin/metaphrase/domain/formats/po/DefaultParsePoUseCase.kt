package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader

class DefaultParsePoUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParsePoUseCase {
    override suspend fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }
        return withContext(dispatchers.io) {
            read(file)
        }
    }

    private fun read(file: File): List<SegmentModel> = runCatching {
        FileReader(file).use { reader ->
            val res = mutableListOf<SegmentModel>()
            val lines = reader.readLines()

            val groups = buildList<List<String>> {
                val group = mutableListOf<String>()
                for (line in lines) {
                    if (line.isEmpty()) {
                        if (group.isNotEmpty()) {
                            add(group.map { it })
                        }
                        group.clear()
                    } else {
                        group += line
                    }
                }
                if (group.isNotEmpty()) {
                    add(group.map { it })
                }
            }

            val regexKey = Regex("^msgid ")
            val regexMessage = Regex("^msgstr([\\d+])? ")
            for (group in groups) {
                val key = group.firstOrNull { s -> regexKey.find(s) != null }?.let { s ->
                    val startIndex = regexKey.find(s)?.value?.length ?: 0
                    s.substring(startIndex, s.length)
                }.orEmpty()
                val message = group.firstOrNull { s -> regexMessage.find(s) != null }?.let { s ->
                    val startIndex = regexMessage.find(s)?.value?.length ?: 0
                    s.substring(startIndex, s.length)
                }.orEmpty()

                if (key.isNotEmpty()) {
                    res += SegmentModel(key = key, text = message)
                }
            }

            res
        }
    }.getOrElse { emptyList() }
}
