package com.github.diegoberaldin.metaphrase.domain.formats.properties

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets
import java.util.*

class DefaultParsePropertiesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParsePropertiesUseCase {
    override suspend fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }

        return withContext(dispatchers.io) {
            runCatching {
                FileReader(file, StandardCharsets.UTF_8).use { reader ->
                    val props = Properties().apply {
                        load(reader)
                    }
                    buildList {
                        for (entry in props.asIterable()) {
                            val key = entry.key as String
                            val text = entry.value as String
                            add(
                                SegmentModel(
                                    key = key,
                                    text = text,
                                ),
                            )
                        }
                    }
                }
            }.getOrElse { emptyList() }
        }
    }
}
