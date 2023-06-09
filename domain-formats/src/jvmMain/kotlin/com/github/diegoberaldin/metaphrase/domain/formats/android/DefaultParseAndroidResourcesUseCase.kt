package com.github.diegoberaldin.metaphrase.domain.formats.android

import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File

internal class DefaultParseAndroidResourcesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParseAndroidResourcesUseCase {

    companion object {
        private const val ELEM_STRING = "string"
        private const val ATTR_NAME = "name"
        private const val ATTR_TRANSLATABLE = "translatable"
    }

    override suspend operator fun invoke(path: String): List<SegmentModel> {
        val file = File(path)
        if (!file.exists() || !file.canRead()) {
            return emptyList()
        }
        return withContext(dispatchers.io) {
            runCatching {
                val res = mutableListOf<SegmentModel>()
                val resourcesNode = parse(file)
                for (elem in resourcesNode.children) {
                    if ((elem as? Node)?.nodeName != ELEM_STRING) continue
                    val key = (elem.attributes[ATTR_NAME] as? String).orEmpty()
                    val translatable = ((elem.attributes[ATTR_TRANSLATABLE] as? String) ?: "true").toBoolean()
                    val text = (elem.children.firstOrNull() as? TextElement)?.text.orEmpty().sanitize()
                    val segment = SegmentModel(
                        key = key,
                        text = text,
                        translatable = translatable,
                    )
                    res += segment
                }
                res
            }.getOrElse { emptyList() }
        }
    }

    private fun String.sanitize(): String {
        val substitutionList = listOf(
            "\\'" to "'",
        )
        return substitutionList.fold(this) { res, it ->
            res.replace(it.first, it.second)
        }
    }
}
