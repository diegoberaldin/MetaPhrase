package windows.usecase

import common.coroutines.CoroutineDispatcherProvider
import common.utils.Constants
import projectdata.SegmentModel
import kotlinx.coroutines.withContext
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml
import java.io.File
import java.io.FileWriter

internal class DefaultExportWindowsResourcesUseCase(
    private val dispatchers: CoroutineDispatcherProvider,
) : ExportWindowsResourcesUseCase {

    companion object {
        private const val ELEM_ROOT = "root"
        private const val ELEM_DATA = "data"
        private const val ELEM_VALUE = "value"
        private const val ATTR_NAME = "name"
        private const val ATTR_SPACE = "xml:space"
    }

    override suspend operator fun invoke(segments: List<SegmentModel>, path: String) {
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
                val content = getXml(segments)
                FileWriter(file).use {
                    it.write(content)
                }
            }.exceptionOrNull()?.also {
                it.printStackTrace()
            }
        }
    }

    private fun getXml(segments: List<SegmentModel>): String {
        val root = xml(ELEM_ROOT) {
            includeXmlProlog = true
            encoding = "UTF-8"

            for (segment in segments) {
                ELEM_DATA {
                    attribute(ATTR_NAME, segment.key)
                    attribute(ATTR_SPACE, "preserve")
                    ELEM_VALUE {
                        text(segment.text.convertPlaceholders())
                    }
                }
            }
        }
        return root.toString(PrintOptions(pretty = true, singleLineTextElements = true, useCharacterReference = false))
    }

    private fun String.convertPlaceholders(): String {
        val matchResults = Constants.PlaceholderRegex.findAll(this).toList()
        var res = ""
        if (matchResults.isEmpty()) {
            res = this
        } else {
            var index = 0
            var staticPosition = 0
            for (match in matchResults) {
                res += this.substring(index, match.range.first)
                val matchString = match.groups["pos"]?.value.orEmpty()
                val position = if (matchString.length > 1) {
                    (matchString.substring(0, matchString.length - 1).toInt() - 1).coerceAtLeast(0)
                } else {
                    val p = staticPosition
                    staticPosition += 1
                    p
                }

                res += "{$position}"
                index = match.range.last + 1
            }
            if (index < this.length) {
                res += this.substring(index, this.length)
            }
        }
        return res
    }
}
