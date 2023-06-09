package com.github.diegoberaldin.metaphrase.domain.formats.po

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

class DefaultExportPoUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportPoUseCase {
    override suspend fun invoke(segments: List<SegmentModel>, path: String, lang: String) {
        val file = File(path)
        if (!file.exists()) {
            runCatching {
                file.createNewFile()
            }
        }
        if (!file.canWrite()) {
            return
        }
        withContext(dispatchers.io) {
            runCatching {
                val content = convertToPo(segments = segments, lang = lang)
                FileWriter(file).use {
                    it.write(content)
                }
            }
        }
    }

    private fun convertToPo(segments: List<SegmentModel>, lang: String): String = buildString {
        append("msgid \"\"\n")
        append("msgstr \"\"\n")
        append(
            """
            "Language: $lang\n"
            "MIME-Version: 1.0\n"
            "Content-Type: text/plain; charset=UTF-8\n"
            "Content-Transfer-Encoding: 8bit\n"
            """.trimIndent(),
        )
        append("\n\n")
        for (segment in segments) {
            append("msgid ${segment.key}\n")
            append("msgstr ${segment.text}\n")
            append("\n")
        }
    }
}
