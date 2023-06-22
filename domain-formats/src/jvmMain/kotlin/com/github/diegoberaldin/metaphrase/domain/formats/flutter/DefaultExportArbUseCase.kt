package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.io.File
import java.io.FileWriter

class DefaultExportArbUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportArbUseCase {
    private val json = Json {
        prettyPrint = true
    }

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
                FileWriter(file).use { writer ->
                    val map = convertToTree(list = segments.map { it.key to it.text })
                    json.encodeToString(map).also {
                        writer.write(it)
                    }
                }
            }
        }
    }

    private fun convertToTree(list: List<Pair<String, String>>): JsonElement {
        val root = mutableMapOf<String, JsonElement>()
        for (elem in list) {
            root[elem.first] = JsonPrimitive(elem.second)
        }
        return JsonObject(root)
    }
}
