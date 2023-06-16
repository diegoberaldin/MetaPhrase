package com.github.diegoberaldin.metaphrase.domain.spellcheck.repo

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.files.FileManager
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class DefaultUserDefinedWordsRepository(
    private val dispatchers: CoroutineDispatcherProvider,
    private val fileManager: FileManager,
) : UserDefinedWordsRepository {

    private fun getWordsFile(lang: String): File {
        val dir = File(fileManager.getFilePath("spelling"))
        dir.mkdirs()
        return File(dir, "$lang.txt")
    }

    override suspend fun getAll(lang: String): List<String> {
        return withContext(dispatchers.io) {
            val file = getWordsFile(lang)
            if (file.exists() && file.canRead()) {
                FileReader(file).use { reader ->
                    reader.readLines().filter { it.isNotEmpty() }
                }
            } else {
                emptyList()
            }
        }
    }

    override suspend fun clear(lang: String) {
        val file = getWordsFile(lang)
        file.delete()
    }

    override suspend fun add(word: String, lang: String) {
        val file = getWordsFile(lang)
        if (!file.exists()) {
            file.createNewFile()
        }
        if (file.canWrite()) {
            FileWriter(file).use { writer ->
                writer.write("$word\n")
            }
        }
    }
}
