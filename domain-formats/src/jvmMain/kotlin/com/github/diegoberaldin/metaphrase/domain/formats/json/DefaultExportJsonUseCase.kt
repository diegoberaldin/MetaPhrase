package com.github.diegoberaldin.metaphrase.domain.formats.json

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

class DefaultExportJsonUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportJsonUseCase {
    private val json = Json {
        prettyPrint = true
    }

    override suspend fun invoke(segments: List<SegmentModel>, path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.createNewFile()
        }
        if (!file.canWrite()) {
            return
        }
        withContext(dispatchers.io) {
            FileWriter(file).use { writer ->
                val map = convertToTree(list = segments.map { it.key to it.text })
                json.encodeToString(map).also {
                    writer.write(it)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun convertToTree(list: List<Pair<String, String>>): JsonElement {
        fun listToTree(keyPath: List<String>, text: String): Map<String, Any> {
            if (keyPath.size == 1) {
                return mutableMapOf(keyPath.first() to text)
            }
            return mutableMapOf(
                keyPath.first() to listToTree(
                    keyPath = keyPath.drop(1),
                    text = text,
                ),
            )
        }

        fun mergeTrees(source: Map<String, Any>, dest: MutableMap<String, Any>) {
            for (key in source.keys) {
                if (key !in dest) {
                    val value = source[key]
                    if (value is Map<*, *>) {
                        dest[key] = source[key] as Map<*, *>
                    } else if (value is String) {
                        dest[key] = source[key] as String
                    }
                } else {
                    mergeTrees(
                        dest = dest[key] as MutableMap<String, Any>,
                        source = source[key] as Map<String, Any>,
                    )
                }
            }
        }

        fun transformToJson(source: Map<String, Any>, dest: MutableMap<String, JsonElement>) {
            for (key in source.keys) {
                val value = source[key]
                if (value is String) {
                    dest[key] = JsonPrimitive(value)
                } else if (value is Map<*, *>) {
                    val n = mutableMapOf<String, JsonElement>()
                    transformToJson(
                        source = value as Map<String, Any>,
                        dest = n,
                    )
                    dest[key] = JsonObject(n)
                }
            }
        }

        val mapRoot = mutableMapOf<String, Any>()
        for (current in list) {
            val key = current.first
            val text = current.second
            val keyTokens = key.split(".")
            val node = listToTree(
                keyPath = keyTokens,
                text = text,
            )
            mergeTrees(
                source = node,
                dest = mapRoot,
            )
        }

        val root = mutableMapOf<String, JsonElement>()
        transformToJson(
            source = mapRoot,
            dest = root,
        )
        return JsonObject(root)
    }
}
