package ios.usecase

import data.SegmentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

internal class DefaultExportIosResourcesUseCase : ExportIosResourcesUseCase {

    override suspend operator fun invoke(segments: List<SegmentModel>, path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        if (!file.canWrite()) {
            return
        }
        withContext(Dispatchers.IO) {
            runCatching {
                val content = getStringTable(segments)
                FileWriter(file).use {
                    it.write(content)
                }
            }
        }
    }

    private fun getStringTable(segments: List<SegmentModel>) = buildString {
        append("// Localizable.strings\n")
        append("\n")
        for (segment in segments) {
            append("\"${segment.key}\" = \"${segment.text.sanitize()}\";\n")
        }
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            Regex("%s") to "%@",
            Regex("%(\\d+)\\\$s") to "%$1\\$\\@",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
