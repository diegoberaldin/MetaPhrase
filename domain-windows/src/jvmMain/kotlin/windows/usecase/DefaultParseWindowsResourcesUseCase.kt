package windows.usecase

import common.coroutines.CoroutineDispatcherProvider
import projectdata.SegmentModel
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File

internal class DefaultParseWindowsResourcesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ParseWindowsResourcesUseCase {

    companion object {
        private const val ELEM_DATA = "data"
        private const val ELEM_VALUE = "value"
        private const val ATTR_NAME = "name"

        private val PLACEHOLDER_REGEX = Regex("\\{(?<pos>\\d+?)}")
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
                    if ((elem as? Node)?.nodeName != ELEM_DATA) continue
                    val key = (elem.attributes[ATTR_NAME] as? String).orEmpty()
                    val value = elem.children.firstOrNull { e -> (e as? Node)?.nodeName == ELEM_VALUE } as? Node
                    if (value != null) {
                        val text = (value.children.firstOrNull() as? TextElement)?.text.orEmpty().convertPlaceholders()
                        val segment = SegmentModel(
                            key = key,
                            text = text,
                        )
                        res += segment
                    }
                }
                res
            }.getOrElse { emptyList() }
        }
    }

    private fun String.convertPlaceholders(): String {
        val matchResults = PLACEHOLDER_REGEX.findAll(this).toList()
        var res = ""
        if (matchResults.isEmpty()) {
            res = this
        } else {
            var index = 0
            for (match in matchResults) {
                res += this.substring(index, matchResults.first().range.first)
                val position = (match.groups["pos"]?.value?.takeIf { it.isNotEmpty() } ?: "0").toInt() + 1
                res += "%$position\$s"
                index = match.range.last + 1
            }
            if (index < this.length) {
                res += this.substring(index, this.length)
            }
        }
        return res
    }
}
