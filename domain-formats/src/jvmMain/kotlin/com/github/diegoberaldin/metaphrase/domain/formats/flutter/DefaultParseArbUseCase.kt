package com.github.diegoberaldin.metaphrase.domain.formats.flutter

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.io.File
import java.io.FileReader

internal class DefaultParseArbUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParseArbUseCase {
    override suspend fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }

        return withContext(dispatchers.io) {
            FileReader(file).use { reader ->
                val root = parseToJsonElement(reader.readText())
                val pairs = process(root).sortedBy { it.first }
                pairs.map {
                    SegmentModel(
                        key = it.first,
                        text = it.second,
                    )
                }
            }
        }
    }

    private fun process(
        element: JsonElement,
    ): List<Pair<String, String>> {
        if (element !is JsonObject) {
            return emptyList()
        }

        val keys = element.keys
            .filter { !it.startsWith("@") }
            .filter { (element[it] as? JsonPrimitive)?.isString == true }
        val pairs = keys.map {
            it to element[it].toString().removeSurrounding("\"")
        }
        return pairs
    }
}
