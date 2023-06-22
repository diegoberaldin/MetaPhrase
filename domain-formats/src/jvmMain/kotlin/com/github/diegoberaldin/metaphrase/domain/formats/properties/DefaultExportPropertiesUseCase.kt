package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.util.*

class DefaultExportPropertiesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportPropertiesUseCase {
    override suspend fun invoke(segments: List<SegmentModel>, path: String) {
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
                FileWriter(file, StandardCharsets.UTF_8).use { writer ->
                    Properties().apply {
                        for (segment in segments) {
                            put(segment.key, segment.text)
                        }
                    }.store(writer, "")
                }
            }
        }
    }
}
