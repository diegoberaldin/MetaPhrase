package com.github.diegoberaldin.metaphrase.domain.formats.json

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.io.File
import java.io.FileReader

internal class DefaultParseJsonUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParseJsonUseCase {
    override suspend fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }

        return withContext(dispatchers.io) {
            runCatching {
                FileReader(file).use { reader ->
                    val root = parseToJsonElement(reader.readText())
                    val pairs = linearize(root).sortedBy { it.first }
                    pairs.map {
                        SegmentModel(
                            key = it.first,
                            text = it.second,
                        )
                    }
                }
            }.getOrElse { emptyList() }
        }
    }

    private fun linearize(
        element: JsonElement,
        prefix: String = "",
    ): List<Pair<String, String>> {
        if (element !is JsonObject) {
            return emptyList()
        }

        fun getThisLevelKey(k: String) = if (prefix.isEmpty()) k else "$prefix.$k"

        val scalarKeys = element.keys.filter { (element[it] as? JsonPrimitive)?.isString == true }
        val scalarValues = scalarKeys.map {
            getThisLevelKey(it) to element[it].toString().removeSurrounding("\"")
        }
        val otherKeys = element.keys.filter { (element[it] as? JsonObject) != null }
        val res = scalarValues.toMutableList()
        for (key in otherKeys) {
            val list = linearize(element = element[key] as JsonElement, prefix = getThisLevelKey(key))
            res += list
        }
        return res
    }
}
