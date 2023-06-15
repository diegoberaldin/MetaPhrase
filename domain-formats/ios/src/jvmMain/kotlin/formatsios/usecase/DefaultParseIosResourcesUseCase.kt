package formatsios.usecase

import common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.withContext
import projectdata.SegmentModel
import java.io.File
import java.io.FileReader

internal class DefaultParseIosResourcesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParseIosResourcesUseCase {

    override suspend operator fun invoke(path: String): List<SegmentModel> {
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
            res
        }
    }.getOrElse { emptyList() }

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
