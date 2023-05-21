package repository.usecase

import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader

class ParseIosResourcesUseCase {

    companion object {
        private const val ELEM_STRING = "string"
        private const val ATTR_NAME = "name"
        private const val ATTR_TRANSLATABLE = "translatable"
    }

    suspend operator fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }
        return withContext(Dispatchers.IO) {
            runCatching {
                val res = mutableListOf<SegmentModel>()

                FileReader(file).use { reader ->
                    val lines = reader.readLines()
                    for (line in lines) {
                        val trimmedLine = line.trim()
                        if (trimmedLine.isEmpty() || trimmedLine.startsWith("//")) {
                            continue
                        }
                        val tokens = trimmedLine.let {
                            if (it.endsWith(";")) it.dropLast(1) else it
                        }.split("=").map {
                            it.trim()
                                .replace(Regex("^\""), "")
                                .replace(Regex("\"$"), "")
                        }
                        if (tokens.size == 2) {
                            val key = tokens.first()
                            val text = tokens[1].sanitize()
                            val segment = SegmentModel(
                                key = key,
                                text = text,
                            )
                            res += segment
                        }
                    }
                }
                res
            }.getOrElse { emptyList() }
        }
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            Regex("%@") to "%s",
            Regex("%(\\d+)\\$@") to "%$1\\$\\s",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
